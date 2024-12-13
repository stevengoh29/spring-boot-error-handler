package io.stevengoh.common.spring_boot_exception_handling.type.http_400_bad_request;

import io.stevengoh.common.spring_boot_exception_handling.handlers.BindingExceptionHandlerTest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bad-request")
public class BadRequestTestRestController {
    @GetMapping("missing-mandatory-params")
    public void doGet(
            @RequestParam String params1,
            @RequestParam String params2
    ) {

    }

    @GetMapping("missing-path-variable/{uuid}")
    public void doGet2(@PathVariable("uuid") String uuid) {

    }

    @PostMapping("missing-mandatory-field")
    public void doPost(@Valid @RequestBody TestBadRequestBody requestBody) {

    }

    @PostMapping("invalid-body")
    public void doPost2(@Valid @RequestBody TestBadRequestBody requestBody) {

    }
}
