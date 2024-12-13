package io.stevengoh.common.spring_boot_exception_handling.exceptions;

import io.stevengoh.common.spring_boot_exception_handling.annotations.ResponseErrorCode;

public class CoreException extends RuntimeException {
    private String source = "CBS";

    public CoreException() {
        super();
    }

    public CoreException(String message) {
        super(message);
    }

    public CoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public CoreException(Throwable cause) {
        super(cause);
    }
}
