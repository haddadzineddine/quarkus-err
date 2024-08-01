package org.cgm.core.models;

import lombok.Builder;

@Builder
public record Error(String code, String message, String description) {
}
