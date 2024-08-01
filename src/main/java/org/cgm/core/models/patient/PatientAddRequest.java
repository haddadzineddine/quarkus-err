package org.cgm.core.models.patient;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record PatientAddRequest(@NotBlank String name) {
}
