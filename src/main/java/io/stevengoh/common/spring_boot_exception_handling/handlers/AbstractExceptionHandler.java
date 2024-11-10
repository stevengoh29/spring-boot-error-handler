package io.stevengoh.common.spring_boot_exception_handling.handlers;

import io.stevengoh.common.spring_boot_exception_handling.ApiExceptionHandler;
import io.stevengoh.common.spring_boot_exception_handling.mapper.ErrorCodeMapper;
import io.stevengoh.common.spring_boot_exception_handling.mapper.ErrorMessageMapper;
import io.stevengoh.common.spring_boot_exception_handling.mapper.HttpStatusCodeMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public abstract class AbstractExceptionHandler implements ApiExceptionHandler {
    protected final HttpStatusCodeMapper httpStatusCodeMapper;
    protected final ErrorCodeMapper errorCodeMapper;
    protected final ErrorMessageMapper errorMessageMapper;

    public AbstractExceptionHandler(HttpStatusCodeMapper httpStatusCodeMapper, ErrorCodeMapper errorCodeMapper, ErrorMessageMapper errorMessageMapper) {
        this.httpStatusCodeMapper = httpStatusCodeMapper;
        this.errorCodeMapper = errorCodeMapper;
        this.errorMessageMapper = errorMessageMapper;
    }

    protected HttpStatusCode getHttpStatusCode(Throwable exception, HttpStatusCode defaultStatusCode) {
        return httpStatusCodeMapper.getStatusCode(exception, defaultStatusCode);
    }

    protected String getErrorCode(Throwable exception) {
        return errorCodeMapper.getErrorCode(exception);
    }

    protected String getErrorMessage(Throwable exception) {
        return errorMessageMapper.getMessage(exception);
    }
}
