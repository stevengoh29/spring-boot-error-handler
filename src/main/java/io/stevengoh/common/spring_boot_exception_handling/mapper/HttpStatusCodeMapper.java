package io.stevengoh.common.spring_boot_exception_handling.mapper;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@Component
public class HttpStatusCodeMapper {
    public HttpStatusCode getStatusCode(Throwable throwable) {
        return getStatusCode(throwable, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public HttpStatusCode getStatusCode(Throwable throwable, HttpStatusCode defaultStatusCode) {
        HttpStatusCode httpStatusCode = getHttpStatusFromAnnotation(throwable.getClass());

        if (httpStatusCode == null) {
            return defaultStatusCode;
        }

        if (throwable instanceof ResponseStatusException) {
            return ((ResponseStatusException) throwable).getStatusCode();
        }

        return httpStatusCode;
    }

    private HttpStatusCode getHttpStatusFromAnnotation(Class<?> classType) {
        if (classType == null) return null;
        ResponseStatus responseStatus = AnnotationUtils.getAnnotation(classType, ResponseStatus.class);
        return responseStatus != null ? responseStatus.value() : null;
    }
}
