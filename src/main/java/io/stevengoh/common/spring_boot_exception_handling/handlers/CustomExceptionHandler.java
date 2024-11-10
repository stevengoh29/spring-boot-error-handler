package io.stevengoh.common.spring_boot_exception_handling.handlers;

import io.stevengoh.common.spring_boot_exception_handling.ApiErrorResponse;
import io.stevengoh.common.spring_boot_exception_handling.exceptions.CustomException;
import io.stevengoh.common.spring_boot_exception_handling.mapper.ErrorCodeMapper;
import io.stevengoh.common.spring_boot_exception_handling.mapper.ErrorMessageMapper;
import io.stevengoh.common.spring_boot_exception_handling.mapper.HttpStatusCodeMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;

@Component
public class CustomExceptionHandler extends AbstractExceptionHandler {
    public CustomExceptionHandler(HttpStatusCodeMapper httpStatusCodeMapper, ErrorCodeMapper errorCodeMapper, ErrorMessageMapper errorMessageMapper) {
        super(httpStatusCodeMapper, errorCodeMapper, errorMessageMapper);
    }

    @Override
    public boolean canHandle(Throwable e) {
        return e instanceof CustomException;
    }

    @Override
    public ApiErrorResponse handle(Throwable e) {
        HttpStatusCode httpStatusCode = getHttpStatusCode(e, HttpStatus.INTERNAL_SERVER_ERROR);
        String errorCode = getErrorCode(e);
        String errorMessage = getErrorMessage(e);

        return ApiErrorResponse.builder()
                .responseCode(errorCode)
                .statusCode(httpStatusCode)
                .message(errorMessage)
                .build();
    }
}
