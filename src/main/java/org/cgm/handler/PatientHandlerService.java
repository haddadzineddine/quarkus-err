package org.cgm.handler;

import java.util.List;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.UUID;
import org.cgm.core.exceptions.patient.PatientNotFound;
import org.cgm.core.models.patient.Patient;
import org.cgm.core.models.patient.PatientAddRequest;
import org.cgm.data.services.PatientService;

@ApplicationScoped
public class PatientHandlerService {

    @Inject
    PatientService patientService;

    public Uni<Patient> create(PatientAddRequest patientAddRequest) {
        return this.patientService.create(patientAddRequest);
    }

    public Uni<Patient> get(UUID uuid) {
        return this.patientService.get(uuid)
                .onItem().ifNull().failWith(PatientNotFound::new);
    }

    public Uni<List<Patient>> getAll() {
        return patientService.getAll();

    }

    public Uni<Long> delete(UUID uuid) {
        return this.patientService.get(uuid)
                .onItem().ifNull().failWith(PatientNotFound::new)
                .chain((patient) -> this.patientService.delete(patient.uuid()));
    }

    public Uni<Patient> update(UUID uuid, PatientAddRequest patientAddRequest) {
        return this.patientService.get(uuid)
                .onItem().ifNull().failWith(PatientNotFound::new)
                .chain((patient) -> this.patientService.update(patient.uuid(), patientAddRequest));
    }
}
