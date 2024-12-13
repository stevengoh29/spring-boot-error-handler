package io.stevengoh.common.spring_boot_exception_handling;

import io.stevengoh.common.spring_boot_exception_handling.exceptions.CustomErrorException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/integration-test")
public class IntegrationTestRestController {

    @GetMapping("/runtime")
    void throwRuntimeException() {
        throw new CustomErrorException("This is a test exception");
    }
}
