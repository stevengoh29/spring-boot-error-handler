package io.stevengoh.common.spring_boot_exception_handling.type.http_400_bad_request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TestBadRequestBody {
    @NotNull
    private String value;

    @NotNull
    @Size(min = 1, max = 255)
    private String value2;

    private Integer value3;
}
