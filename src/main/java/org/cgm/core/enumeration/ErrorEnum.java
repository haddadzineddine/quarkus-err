package org.cgm.core.enumeration;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ErrorEnum {

    E00001("E00001", "Patient Not Found",
            "The patient that you are trying to get/use doesn't existe"),
    E00002("E00002", "Visite Not Found",
            "The visite that you are trying to get/use doesn't existe");

    @Getter
    private final String code;

    @Getter
    private final String message;

    @Getter
    private final String description;

    public static boolean containsCode(String code) {
        return Arrays.stream(ErrorEnum.values())
                .anyMatch(errorEnum -> errorEnum.getCode().equals(code));
    }

}
