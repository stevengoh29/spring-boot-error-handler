package io.stevengoh.common.spring_boot_exception_handling.mapper;

import io.stevengoh.common.spring_boot_exception_handling.annotations.ResponseErrorCode;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

@Component
public class ErrorCodeMapper {
    public String getErrorCode(Throwable exception) {
        String code = getErrorCodeFromPropertiesOrAnnotation(exception.getClass());
        return code == null ? "GENERAL_ERROR" : code;
    }

    private String getErrorCodeFromPropertiesOrAnnotation(Class<?> exceptionClass) {
        if (exceptionClass == null) return null;
        ResponseErrorCode errorCodeAnnotation = AnnotationUtils.getAnnotation(exceptionClass, ResponseErrorCode.class);
        return errorCodeAnnotation != null ? errorCodeAnnotation.value() : null;
    }
}
