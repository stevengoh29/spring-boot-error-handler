package io.stevengoh.common.spring_boot_exception_handling;

import io.stevengoh.common.spring_boot_exception_handling.exceptions.CustomException;
import io.stevengoh.common.spring_boot_exception_handling.handlers.CustomExceptionHandler;
import io.stevengoh.common.spring_boot_exception_handling.mapper.ErrorCodeMapper;
import io.stevengoh.common.spring_boot_exception_handling.mapper.ErrorMessageMapper;
import io.stevengoh.common.spring_boot_exception_handling.mapper.HttpStatusCodeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
public abstract class AbstractErrorHandlingConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public ErrorHandlingFacade errorHandlingFacade(
            List<ApiExceptionHandler> handlers,
            FallbackApiExceptionHandler fallbackHandler
    ) {
        handlers.sort(AnnotationAwareOrderComparator.INSTANCE);
        log.info("Error Handling Spring Boot Starter active with {} handlers", handlers.size());
        log.debug("Handlers: {}", handlers);

        return new ErrorHandlingFacade(handlers, fallbackHandler);
    }

    @Bean
    @ConditionalOnMissingBean
    public HttpStatusCodeMapper httpStatusMapper() {
        return new HttpStatusCodeMapper();
    }

    @Bean
    @ConditionalOnMissingBean
    public ErrorCodeMapper errorCodeMapper() {
        return new ErrorCodeMapper();
    }

    @Bean
    @ConditionalOnMissingBean
    public ErrorMessageMapper errorMessageMapper() {
        return new ErrorMessageMapper();
    }

    @Bean
    @ConditionalOnMissingBean
    public FallbackApiExceptionHandler defaultHandler(HttpStatusCodeMapper httpStatusMapper,
                                                      ErrorCodeMapper errorCodeMapper,
                                                      ErrorMessageMapper errorMessageMapper) {
        return new DefaultFallbackApiExceptionHandler(httpStatusMapper,
                errorCodeMapper,
                errorMessageMapper);
    }

    @Bean
    @ConditionalOnMissingBean
    public CustomExceptionHandler customExceptionHandler(
            HttpStatusCodeMapper httpStatusCodeMapper,
            ErrorCodeMapper errorCodeMapper,
            ErrorMessageMapper errorMessageMapper
    ) {
        return new CustomExceptionHandler(httpStatusCodeMapper, errorCodeMapper, errorMessageMapper);
    }
}
