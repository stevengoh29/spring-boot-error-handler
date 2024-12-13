package io.stevengoh.common.spring_boot_exception_handling.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.stevengoh.common.spring_boot_exception_handling.ApiErrorResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {
        HttpMessageNotReadableExceptionHandlerTest.TestController.class
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class HttpMessageNotReadableExceptionHandlerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

//    @Test
    void testHttpMessageNotReadableException() throws Exception {
        MvcResult result = mockMvc.perform(post("/test/validation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{invalidjsonhere}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("responseCode").value("02"))
                .andExpect(jsonPath("message").value("GENERAL"))
                .andReturn()
        ;

        String jsonResult = result.getResponse().getContentAsString();
        ApiErrorResponse apiErrorResponse = objectMapper.readValue(jsonResult, ApiErrorResponse.class);

        assertNotNull(apiErrorResponse);
//        assertEquals("GENERAL_ERROR", apiErrorResponse.getResponseCode());

        // Pretty print the JSON response
        ObjectMapper prettyPrinter = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        String prettyJson = prettyPrinter.writeValueAsString(apiErrorResponse);

        // Log or print the beautified JSON
        System.out.println(prettyJson);
    }

    @RestController
    @RequestMapping("/test/validation")
    public static class TestController {
        @PostMapping
        public void doPost(@Valid @RequestBody TestRequestBody requestBody) {

        }
    }

    @Getter
    @Setter
    public static class TestRequestBody {
        @NotNull
        private String value;

        @NotNull
        @Size(min = 1, max = 255)
        private String value2;
    }
}
