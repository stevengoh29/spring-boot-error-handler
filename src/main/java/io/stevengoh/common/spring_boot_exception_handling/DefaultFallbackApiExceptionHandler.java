package io.stevengoh.common.spring_boot_exception_handling;

import io.stevengoh.common.spring_boot_exception_handling.mapper.ErrorCodeMapper;
import io.stevengoh.common.spring_boot_exception_handling.mapper.ErrorMessageMapper;
import io.stevengoh.common.spring_boot_exception_handling.mapper.HttpStatusCodeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DefaultFallbackApiExceptionHandler implements FallbackApiExceptionHandler {
    private final HttpStatusCodeMapper httpStatusMapper;
    private final ErrorCodeMapper errorCodeMapper;
    private final ErrorMessageMapper errorMessageMapper;

    public DefaultFallbackApiExceptionHandler(HttpStatusCodeMapper httpStatusMapper, ErrorCodeMapper errorCodeMapper, ErrorMessageMapper errorMessageMapper) {
        this.httpStatusMapper = httpStatusMapper;
        this.errorCodeMapper = errorCodeMapper;
        this.errorMessageMapper = errorMessageMapper;
    }

    @Override
    public ApiErrorResponse handle(Throwable throwable) {
        HttpStatusCode httpStatusCode = httpStatusMapper.getStatusCode(throwable, HttpStatus.INTERNAL_SERVER_ERROR);
        String errorCode = errorCodeMapper.getErrorCode(throwable);
        String errorMessage = errorMessageMapper.getMessage(throwable);

        return ApiErrorResponse.builder()
                .statusCode(httpStatusCode)
                .responseCode(errorCode)
                .message(errorMessage)
                .build();
    }
}
