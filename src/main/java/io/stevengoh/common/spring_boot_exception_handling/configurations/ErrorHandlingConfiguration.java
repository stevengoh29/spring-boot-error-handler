package io.stevengoh.common.spring_boot_exception_handling.configurations;

import io.stevengoh.common.spring_boot_exception_handling.AbstractErrorHandlingConfiguration;
import io.stevengoh.common.spring_boot_exception_handling.ErrorHandlingFacade;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class ErrorHandlingConfiguration extends AbstractErrorHandlingConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public ErrorHandlingControllerAdvice errorHandlingControllerAdvice(ErrorHandlingFacade errorHandlingFacade) {
        return new ErrorHandlingControllerAdvice(errorHandlingFacade);
    }
}
