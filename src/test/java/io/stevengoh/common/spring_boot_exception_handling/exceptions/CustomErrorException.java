package io.stevengoh.common.spring_boot_exception_handling.exceptions;

import io.stevengoh.common.spring_boot_exception_handling.annotations.ResponseErrorCode;

@ResponseErrorCode("03")
public class CustomErrorException extends RuntimeException{
    public CustomErrorException() {
        super();
    }

    public CustomErrorException(String message) {
        super(message);
    }

    public CustomErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomErrorException(Throwable cause) {
        super(cause);
    }
}
