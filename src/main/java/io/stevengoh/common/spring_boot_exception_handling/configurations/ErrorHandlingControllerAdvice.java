package io.stevengoh.common.spring_boot_exception_handling.configurations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.stevengoh.common.spring_boot_exception_handling.ApiErrorResponse;
import io.stevengoh.common.spring_boot_exception_handling.ErrorHandlingFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice(annotations = RestController.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class ErrorHandlingControllerAdvice implements ResponseBodyAdvice<Object> {
    private static final Logger log = LoggerFactory.getLogger("MSG_LOG");
    private final ErrorHandlingFacade errorHandlingFacade;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ErrorHandlingControllerAdvice(ErrorHandlingFacade errorHandlingFacade) {
        this.errorHandlingFacade = errorHandlingFacade;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // Log the response body
        if (body != null) {
            log.info("Response to server: {}", body);
        }

        return body; // Return the body as-is
    }

    @ExceptionHandler
    public ResponseEntity<?> handleExceptions(Throwable exception, WebRequest request) throws JsonProcessingException {
        ApiErrorResponse apiErrorResponse = errorHandlingFacade.handle(exception);
        log.error(exception.getMessage(), exception);
        log.info("Response: {} ", objectMapper.writeValueAsString(apiErrorResponse));
        return ResponseEntity.status(apiErrorResponse.getStatusCode()).body(apiErrorResponse);
    }
}
