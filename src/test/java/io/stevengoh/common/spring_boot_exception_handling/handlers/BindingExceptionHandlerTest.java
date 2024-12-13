package io.stevengoh.common.spring_boot_exception_handling.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.stevengoh.common.spring_boot_exception_handling.ApiErrorResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.annotation.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {
        BindingExceptionHandlerTest.BadRequestExceptionTestController.class
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BindingExceptionHandlerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

//    @Test
    void testMethodArgumentNotValidException() throws Exception {
         MvcResult result = mockMvc.perform(post("/test/validation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"value2\": \"\"}"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        ApiErrorResponse apiErrorResponse = objectMapper.readValue(jsonResult, ApiErrorResponse.class);

        assertNotNull(apiErrorResponse);
        assertEquals("INVALID_REQUEST", apiErrorResponse.getResponseCode());

        // Pretty print the JSON response
        ObjectMapper prettyPrinter = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        String prettyJson = prettyPrinter.writeValueAsString(apiErrorResponse);

        // Log or print the beautified JSON
        System.out.println(prettyJson);
    }

    @RestController
    @RequestMapping("/test/validation")
    public static class BadRequestExceptionTestController {
        @PostMapping
        public void doPost(@Valid @RequestBody TestBadRequestBody requestBody) {

        }

        @GetMapping
        public void doGet(
                @RequestParam() String params1,
                @RequestParam String params2
        ) {

        }
    }

    @Getter
    @Setter
    public static class TestBadRequestBody {
        @NotNull
        private String value;

        @NotNull
        @Size(min = 1, max = 255)
        private String value2;
    }
}
