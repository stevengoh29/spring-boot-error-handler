package io.stevengoh.common.spring_boot_exception_handling;

public interface ApiExceptionHandler {
    boolean canHandle(Throwable e);
    ApiErrorResponse handle(Throwable e);
}
