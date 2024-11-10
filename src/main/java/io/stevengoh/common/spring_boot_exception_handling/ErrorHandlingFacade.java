package io.stevengoh.common.spring_boot_exception_handling;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
public class ErrorHandlingFacade {
    private final List<ApiExceptionHandler> exceptionHandlers;
    private final FallbackApiExceptionHandler fallbackApiExceptionHandler;

    public ErrorHandlingFacade(List<ApiExceptionHandler> exceptionHandlers, FallbackApiExceptionHandler fallbackApiExceptionHandler) {
        this.exceptionHandlers = exceptionHandlers;
        this.fallbackApiExceptionHandler = fallbackApiExceptionHandler;
    }

    public ApiErrorResponse handle(Throwable exception) {
        ApiErrorResponse errorResponse = null;
        for (ApiExceptionHandler handler : exceptionHandlers) {
            if (handler.canHandle(exception)) {
                errorResponse = handler.handle(exception);
                break;
            }
        }

        if (errorResponse == null) {
            errorResponse = fallbackApiExceptionHandler.handle(exception);
        }

        return errorResponse;
    }
}
