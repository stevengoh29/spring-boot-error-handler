package io.stevengoh.common.spring_boot_exception_handling.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.stevengoh.common.spring_boot_exception_handling.ApiErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.annotation.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {
        MissingRequestValueExceptionHandlerTest.MissingRequestValueExceptionHandlerTestController.class
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MissingRequestValueExceptionHandlerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

//    @Test
    void testMissingQueryParamsException() throws Exception {
        MvcResult result = mockMvc.perform(get("/test/missing-request-value/query-params")
                        .param("params1", "abc"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        ApiErrorResponse apiErrorResponse = objectMapper.readValue(jsonResult, ApiErrorResponse.class);

        assertNotNull(apiErrorResponse);
        assertEquals("GENERAL_ERROR", apiErrorResponse.getResponseCode());

        // Pretty print the JSON response
        ObjectMapper prettyPrinter = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        String prettyJson = prettyPrinter.writeValueAsString(apiErrorResponse);

        // Log or print the beautified JSON
        System.out.println(prettyJson);
    }

//    @Test
    void testMissingPathVariableException() throws Exception {
        MvcResult result = mockMvc.perform(get("/test/missing-request-value/path-variable"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        ApiErrorResponse apiErrorResponse = objectMapper.readValue(jsonResult, ApiErrorResponse.class);

        assertNotNull(apiErrorResponse);
        assertEquals("GENERAL_ERROR", apiErrorResponse.getResponseCode());

        // Pretty print the JSON response
        ObjectMapper prettyPrinter = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        String prettyJson = prettyPrinter.writeValueAsString(apiErrorResponse);

        // Log or print the beautified JSON
        System.out.println(prettyJson);
    }

//    @Test
    void testMissingCookiesException() throws Exception {
        MvcResult result = mockMvc.perform(get("/test/missing-request-value/cookies"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        ApiErrorResponse apiErrorResponse = objectMapper.readValue(jsonResult, ApiErrorResponse.class);

        assertNotNull(apiErrorResponse);
        assertEquals("GENERAL_ERROR", apiErrorResponse.getResponseCode());

        // Pretty print the JSON response
        ObjectMapper prettyPrinter = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        String prettyJson = prettyPrinter.writeValueAsString(apiErrorResponse);

        // Log or print the beautified JSON
        System.out.println(prettyJson);
    }

//    @Test
    void testMissingHeadersException() throws Exception {
        MvcResult result = mockMvc.perform(get("/test/missing-request-value/headers"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        ApiErrorResponse apiErrorResponse = objectMapper.readValue(jsonResult, ApiErrorResponse.class);

        assertNotNull(apiErrorResponse);
        assertEquals("GENERAL_ERROR", apiErrorResponse.getResponseCode());

        // Pretty print the JSON response
        ObjectMapper prettyPrinter = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        String prettyJson = prettyPrinter.writeValueAsString(apiErrorResponse);

        // Log or print the beautified JSON
        System.out.println(prettyJson);
    }

    @RestController
    @RequestMapping("/test/missing-request-value")
    public static class MissingRequestValueExceptionHandlerTestController {
        @GetMapping("/query-params")
        public void testMissingQueryParamsController(@RequestParam String params1, @RequestParam String params2) {
        }

        @GetMapping("/path-variable")
        public void testMissingPathVariableController(@PathVariable("id") String id) {
        }

        @GetMapping("/cookies")
        public void testMissingCookiesController(@CookieValue("test") String cookie) {
        }

        @GetMapping("/headers")
        public void testMissingHeadersController(@RequestHeader("X-TEST-ID") String testId) {
        }
    }
}
