package io.stevengoh.common.spring_boot_exception_handling;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiErrorField {
    private final String code;
    private final String property;
    private final String message;
    private final Object rejectedValue;
}
