package io.stevengoh.common.spring_boot_exception_handling;

public interface FallbackApiExceptionHandler {
    ApiErrorResponse handle(Throwable throwable);
}
