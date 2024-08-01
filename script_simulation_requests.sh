#!/bin/bash

# Function to generate a v4 UUID
generate_uuid() {
    if command -v uuidgen > /dev/null 2>&1; then
        uuidgen
    else
        # Fallback if uuidgen is not available
        od -x /dev/urandom | head -1 | awk '{OFS="-"; print $2$3,$4,$5,$6,$7$8$9}' | sed 's/^/xxxxxxxx-xxxx-4xxx-yxxx-/' | sed 's/x/[0-9a-f]/g' | sed 's/y/[89ab]/g' | tr '[A-F]' '[a-f]'
    fi
}

# Function to make the call DELETE
make_request() {
    uuid=$(generate_uuid)
    url="http://localhost:8080/patients/$uuid"
    curl -X DELETE -s -o /dev/null -w "%{http_code}\n" "$url"
}

# Maximum number of concurrent calls
max_concurrent=10

# Runtime in seconds (5 minutes = 300 seconds)
duration=300

# Start time
start_time=$(date +%s)

# Table for storing background process PIDs
pids=()

# Main loop
while true; do
    current_time=$(date +%s)
    elapsed=$((current_time - start_time))

    if [ $elapsed -ge $duration ]; then
        echo "5 minutes elapsed. Stop calls."
        break
    fi

    # Check the number of running processes and start new ones if necessary
    while [ ${#pids[@]} -lt $max_concurrent ]; do
        make_request &
        pids+=($!)
    done

    # Check and delete terminated processes
    for pid in "${pids[@]}"; do
        if ! kill -0 $pid 2>/dev/null; then
            pids=(${pids[@]/$pid})
        fi
    done

    # Short pause to avoid overloading the CPU
    sleep 0.1
done

# Wait for all calls to end
for pid in "${pids[@]}"; do
    wait $pid
done

echo "All calls have been made."