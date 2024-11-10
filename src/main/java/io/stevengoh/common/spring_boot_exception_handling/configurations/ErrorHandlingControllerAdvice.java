package io.stevengoh.common.spring_boot_exception_handling.configurations;

import io.stevengoh.common.spring_boot_exception_handling.ApiErrorResponse;
import io.stevengoh.common.spring_boot_exception_handling.ErrorHandlingFacade;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice(annotations = RestController.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class ErrorHandlingControllerAdvice {
    private final ErrorHandlingFacade errorHandlingFacade;

    public ErrorHandlingControllerAdvice(ErrorHandlingFacade errorHandlingFacade) {
        this.errorHandlingFacade = errorHandlingFacade;
    }

    @ExceptionHandler
    public ResponseEntity<?> handleExceptions(Throwable exception, WebRequest request) {
        ApiErrorResponse apiErrorResponse = errorHandlingFacade.handle(exception);
        return ResponseEntity.status(apiErrorResponse.getStatusCode()).body(apiErrorResponse);
    }
}
