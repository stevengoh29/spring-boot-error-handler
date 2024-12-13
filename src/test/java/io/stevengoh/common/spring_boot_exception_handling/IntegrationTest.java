package io.stevengoh.common.spring_boot_exception_handling;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = IntegrationTestRestController.class)
@Slf4j
public class IntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testRuntimeException() throws Exception {
        MvcResult result = mockMvc.perform(get("/integration-test/runtime"))
                .andExpect(status().isInternalServerError())
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        ApiErrorResponse apiErrorResponse = objectMapper.readValue(jsonResult, ApiErrorResponse.class);
        ObjectMapper prettyPrinter = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        String prettyJson = prettyPrinter.writeValueAsString(apiErrorResponse);
        System.out.println(prettyJson);
    }
}
