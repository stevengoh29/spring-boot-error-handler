package io.stevengoh.common.spring_boot_exception_handling;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = IntegrationTestRestController.class)
public class IntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testRuntimeException() throws Exception {
        mockMvc.perform(get("/integration-test/runtime"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testCustomException() throws Exception {
        mockMvc.perform(get("/integration-test/custom"))
                .andExpect(status().isConflict());
    }
}
