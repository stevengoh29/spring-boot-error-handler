package io.stevengoh.common.spring_boot_exception_handling.handlers;

import io.stevengoh.common.spring_boot_exception_handling.ApiErrorParameter;
import io.stevengoh.common.spring_boot_exception_handling.ApiErrorResponse;
import io.stevengoh.common.spring_boot_exception_handling.mapper.ErrorCodeMapper;
import io.stevengoh.common.spring_boot_exception_handling.mapper.ErrorMessageMapper;
import io.stevengoh.common.spring_boot_exception_handling.mapper.HttpStatusCodeMapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.*;

import java.util.ArrayList;
import java.util.List;

public class MissingRequestValueExceptionHandler extends AbstractExceptionHandler {
    public MissingRequestValueExceptionHandler(HttpStatusCodeMapper httpStatusCodeMapper, ErrorCodeMapper errorCodeMapper, ErrorMessageMapper errorMessageMapper) {
        super(httpStatusCodeMapper, errorCodeMapper, errorMessageMapper);
    }

    @Override
    public boolean canHandle(Throwable exception) {
        return exception instanceof MissingRequestValueException;
    }

    @Override
    public ApiErrorResponse handle(Throwable exception) {
        ApiErrorResponse response = new ApiErrorResponse(
                HttpStatus.BAD_REQUEST,
                "02",
                getErrorMessage(exception)
        );

        if (exception instanceof MissingMatrixVariableException) {
            response.addErrorProperty("variableName", ((MissingMatrixVariableException) exception).getVariableName());
            addParameterInfo(response, ((MissingMatrixVariableException) exception).getParameter());
        } else if (exception instanceof MissingPathVariableException) {
            response.addErrorProperty("variableName", ((MissingPathVariableException) exception).getVariableName());
            addParameterInfo(response, ((MissingPathVariableException) exception).getParameter());
        } else if (exception instanceof MissingRequestCookieException) {
            response.addErrorProperty("cookieName", ((MissingRequestCookieException) exception).getCookieName());
            addParameterInfo(response, ((MissingRequestCookieException) exception).getParameter());
        } else if (exception instanceof MissingRequestHeaderException) {
            response.addErrorProperty("headerName", ((MissingRequestHeaderException) exception).getHeaderName());
            addParameterInfo(response, ((MissingRequestHeaderException) exception).getParameter());
        } else if (exception instanceof MissingServletRequestParameterException) {
            String parameterName = ((MissingServletRequestParameterException) exception).getParameterName();
            String parameterType = ((MissingServletRequestParameterException) exception).getParameterType();
            response.addErrorProperty("parameterName", parameterName);
            response.addErrorProperty("parameterType", parameterType);
        }

        return response;
    }

    private void addParameterInfo(ApiErrorResponse response, MethodParameter parameter) {
        response.addErrorProperty("parameterName", parameter.getParameterName());
        response.addErrorProperty("parameterType", parameter.getParameterType().getSimpleName());
    }
}
