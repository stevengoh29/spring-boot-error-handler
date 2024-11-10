package io.stevengoh.common.spring_boot_exception_handling;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Builder
public class ApiErrorResponse {
    @JsonIgnore
    @Getter
    @Setter
    private HttpStatusCode statusCode;
    @Getter
    @Setter
    private String responseCode;
    @Getter
    @Setter
    private String message;

    private List<String> errorField = new ArrayList<>();
}
