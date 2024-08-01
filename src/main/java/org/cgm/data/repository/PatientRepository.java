package org.cgm.data.repository;

import java.util.UUID;

import org.cgm.data.entity.PatientEntity;
import java.util.List;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PatientRepository implements PanacheRepository<PatientEntity> {

    public Uni<PatientEntity> findByUuid(UUID uuid) {
        return find("uuid = ?1", uuid).firstResult();
    }

    public Uni<List<PatientEntity>> getAll() {
        return findAll().list();
    }

    public Uni<Long> delete(UUID uuid) {
        return delete("uuid", uuid);
    }
}
