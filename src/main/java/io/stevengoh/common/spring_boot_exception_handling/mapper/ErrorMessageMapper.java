package io.stevengoh.common.spring_boot_exception_handling.mapper;

import org.springframework.stereotype.Component;

@Component
public class ErrorMessageMapper {
    public String getMessage(Throwable throwable) {
        return throwable.getMessage();
    }
}
