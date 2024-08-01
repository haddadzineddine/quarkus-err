package org.cgm.core.mappers;

import org.cgm.core.models.patient.Patient;
import org.cgm.core.models.patient.PatientAddRequest;
import org.cgm.data.entity.PatientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Mapper(componentModel = ComponentModel.JAKARTA)
public interface PatientMapper {

    @Mapping(target = "uuid", ignore = true)
    PatientEntity patientAddRequestToPatientEntity(PatientAddRequest patientAddRequest);

    Patient patientEntityToPatient(PatientEntity patientEntity);

}