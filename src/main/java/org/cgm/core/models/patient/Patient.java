package org.cgm.core.models.patient;

import java.util.UUID;
import lombok.Builder;

@Builder
public record Patient(UUID uuid, String name) {

}
