package org.cgm.core.exceptions.config;

import org.cgm.core.enumeration.ErrorEnum;

import lombok.Getter;

@Getter
public class LocalException extends RuntimeException {
    final ErrorEnum error;
    final String description;

    public LocalException(ErrorEnum error, String description) {
        this.error = error;
        this.description = description;
    }

    public LocalException(ErrorEnum error) {
        this.error = error;
        this.description = error.getDescription();
    }

}
