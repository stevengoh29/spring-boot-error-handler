package io.stevengoh.common.spring_boot_exception_handling.handlers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import io.stevengoh.common.spring_boot_exception_handling.ApiErrorField;
import io.stevengoh.common.spring_boot_exception_handling.ApiErrorResponse;
import io.stevengoh.common.spring_boot_exception_handling.mapper.ErrorCodeMapper;
import io.stevengoh.common.spring_boot_exception_handling.mapper.ErrorMessageMapper;
import io.stevengoh.common.spring_boot_exception_handling.mapper.HttpStatusCodeMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpMessageNotReadableExceptionHandler extends AbstractExceptionHandler {
    public HttpMessageNotReadableExceptionHandler(HttpStatusCodeMapper httpStatusCodeMapper, ErrorCodeMapper errorCodeMapper, ErrorMessageMapper errorMessageMapper) {
        super(httpStatusCodeMapper, errorCodeMapper, errorMessageMapper);
    }

    @Override
    public boolean canHandle(Throwable exception) {
        return exception instanceof HttpMessageNotReadableException;
    }

    @Override
    public ApiErrorResponse handle(Throwable exception) {
        HttpMessageNotReadableException ex = (HttpMessageNotReadableException) exception;
        System.out.println(ex.getHttpInputMessage());
        System.out.println(ex.getMessage());

        ApiErrorResponse errorResponse = new ApiErrorResponse(
                HttpStatus.BAD_REQUEST,
                getCustomErrorCode(ex),
                getErrorMessage(exception)
        );

        Map<String, Object> errorProperties = getErrorFields(ex);
        if(errorProperties != null) errorResponse.addErrorProperties(errorProperties);

        return errorResponse;
    }

    private String getCustomErrorCode(HttpMessageNotReadableException exception) {
        Throwable cause = exception.getMostSpecificCause();

        if (cause instanceof InvalidFormatException) {
            return "01";
        }

        return "00";
    }

    private Map<String, Object> getErrorFields(HttpMessageNotReadableException exception) {
        Throwable cause = exception.getMostSpecificCause();
//        List<ApiErrorField> errorFields = new ArrayList<>();
        Map<String, Object> errorProperty = new HashMap<>();

        if (cause instanceof InvalidFormatException invalidFormatEx) {
            List<JsonMappingException.Reference> path = invalidFormatEx.getPath();

            for (JsonMappingException.Reference ref : path) {
                String fieldName = ref.getFieldName();
                String targetType = invalidFormatEx.getTargetType().getSimpleName();
                Object rejectedValue = invalidFormatEx.getValue();

                errorProperty.put("field", fieldName);
                errorProperty.put("message", "Expected type: " + targetType);
                errorProperty.put("rejectedValue", rejectedValue);
            }

            return errorProperty;
        }

        return null;
    }
}
