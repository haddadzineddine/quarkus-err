#!/bin/bash

# Fonction pour générer un UUID v4
generate_uuid() {
    if command -v uuidgen > /dev/null 2>&1; then
        uuidgen
    else
        # Fallback si uuidgen n'est pas disponible
        od -x /dev/urandom | head -1 | awk '{OFS="-"; print $2$3,$4,$5,$6,$7$8$9}' | sed 's/^/xxxxxxxx-xxxx-4xxx-yxxx-/' | sed 's/x/[0-9a-f]/g' | sed 's/y/[89ab]/g' | tr '[A-F]' '[a-f]'
    fi
}

# Fonction pour effectuer l'appel DELETE
make_request() {
    uuid=$(generate_uuid)
    url="http://localhost:8080/patients/$uuid"
    curl -X DELETE -s -o /dev/null -w "%{http_code}\n" "$url"
}

# Nombre maximum d'appels concurrents
max_concurrent=10

# Durée d'exécution en secondes (5 minutes = 300 secondes)
duration=300

# Temps de départ
start_time=$(date +%s)

# Tableau pour stocker les PIDs des processus en arrière-plan
pids=()

# Boucle principale
while true; do
    current_time=$(date +%s)
    elapsed=$((current_time - start_time))

    if [ $elapsed -ge $duration ]; then
        echo "5 minutes écoulées. Arrêt des appels."
        break
    fi

    # Vérifier le nombre de processus en cours et en lancer de nouveaux si nécessaire
    while [ ${#pids[@]} -lt $max_concurrent ]; do
        make_request &
        pids+=($!)
    done

    # Vérifier et supprimer les processus terminés
    for pid in "${pids[@]}"; do
        if ! kill -0 $pid 2>/dev/null; then
            pids=(${pids[@]/$pid})
        fi
    done

    # Petite pause pour éviter de surcharger le CPU
    sleep 0.1
done

# Attendre que tous les appels en cours se terminent
for pid in "${pids[@]}"; do
    wait $pid
done

echo "Tous les appels ont été effectués."