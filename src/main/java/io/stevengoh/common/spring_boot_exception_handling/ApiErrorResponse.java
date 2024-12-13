package io.stevengoh.common.spring_boot_exception_handling;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatusCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
@Setter
public class ApiErrorResponse {
    @JsonIgnore
    private final HttpStatusCode statusCode;
    private final String responseCode;
    private final String message;
    private final Map<String, Object> properties = new HashMap<>();
    private final List<ApiErrorField> errorField = new ArrayList<>();
    private final List<ApiErrorParameter> errorParameters = new ArrayList<>();

    public ApiErrorResponse(HttpStatusCode statusCode, String responseCode, String message) {
        this.statusCode = statusCode;
        this.responseCode = responseCode;
        this.message = message;
    }

    public void addErrorProperties(Map<String, Object> errorProperties) {
        properties.putAll(errorProperties);
    }

    public void addErrorProperty(String propertyName, Object propertyValue) {
        properties.put(propertyName, propertyValue);
    }

    public void addFieldError(ApiErrorField fieldError) {
        errorField.add(fieldError);
    }

    public void addParameterError(ApiErrorParameter parameterError) {
        errorParameters.add(parameterError);
    }
}
