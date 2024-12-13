package io.stevengoh.common.spring_boot_exception_handling.handlers;

import io.stevengoh.common.spring_boot_exception_handling.ApiErrorField;
import io.stevengoh.common.spring_boot_exception_handling.ApiErrorResponse;
import io.stevengoh.common.spring_boot_exception_handling.mapper.ErrorCodeMapper;
import io.stevengoh.common.spring_boot_exception_handling.mapper.ErrorMessageMapper;
import io.stevengoh.common.spring_boot_exception_handling.mapper.HttpStatusCodeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Slf4j
@Component
public class BindingExceptionHandler extends AbstractExceptionHandler {
    public BindingExceptionHandler(HttpStatusCodeMapper httpStatusCodeMapper, ErrorCodeMapper errorCodeMapper, ErrorMessageMapper errorMessageMapper) {
        super(httpStatusCodeMapper, errorCodeMapper, errorMessageMapper);
    }

    @Override
    public boolean canHandle(Throwable ex) {
        return ex instanceof BindingResult;
    }

    @Override
    public ApiErrorResponse handle(Throwable ex) {
        BindingResult bindingResult = (BindingResult) ex;

        ApiErrorResponse response = new ApiErrorResponse(
                getHttpStatusCode(ex, HttpStatus.BAD_REQUEST),
                "02",
                this.getMessage(bindingResult)
        );

        if (bindingResult.hasFieldErrors()) {
            bindingResult.getFieldErrors().stream()
                    .map(fieldError -> new ApiErrorField(
                            getCode(fieldError),
                            fieldError.getField(),
                            getMessage(fieldError),
                            fieldError.getRejectedValue()))
                    .forEach(response::addFieldError);
        }

        return response;
    }

    private String getCode(FieldError fieldError) {
        return fieldError.getCode();
    }

    private String getMessage(FieldError fieldError) {
        return fieldError.getDefaultMessage();
    }

    private String getMessage(BindingResult bindingResult) {
        return "Validation failed for object='" + bindingResult.getObjectName() + "'. Error count: " + bindingResult.getErrorCount();
    }
}
