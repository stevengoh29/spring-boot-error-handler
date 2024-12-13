package io.stevengoh.common.spring_boot_exception_handling.type.http_400_bad_request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.stevengoh.common.spring_boot_exception_handling.ApiErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = BadRequestTestRestController.class)
public class BadRequestTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void errorBodyParsing() throws Exception {
        MvcResult result = mockMvc.perform(post("/bad-request/invalid-body")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{invalid_json_here}")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("responseCode").value("00"))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        ApiErrorResponse apiErrorResponse = objectMapper.readValue(jsonResult, ApiErrorResponse.class);
        ObjectMapper prettyPrinter = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        String prettyJson = prettyPrinter.writeValueAsString(apiErrorResponse);
        System.out.println(prettyJson);
    }

    @Test
    public void errorInvalidFieldFormat() throws Exception {
        MvcResult result = mockMvc.perform(post("/bad-request/missing-mandatory-field")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"value\": \"abc\"," +
                                "\"value2\": \"amazing\"," +
                                "\"value3\": \"number\"}"
                        )
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("responseCode").value("01"))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        ApiErrorResponse apiErrorResponse = objectMapper.readValue(jsonResult, ApiErrorResponse.class);
        ObjectMapper prettyPrinter = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        String prettyJson = prettyPrinter.writeValueAsString(apiErrorResponse);
        System.out.println(prettyJson);
    }

    @Test
    public void errorMissingMandatoryField() throws Exception {
        MvcResult result = mockMvc.perform(post("/bad-request/missing-mandatory-field")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"value2\": \"\"}")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("responseCode").value("02"))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        ApiErrorResponse apiErrorResponse = objectMapper.readValue(jsonResult, ApiErrorResponse.class);
        ObjectMapper prettyPrinter = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        String prettyJson = prettyPrinter.writeValueAsString(apiErrorResponse);
        System.out.println(prettyJson);
    }

    @Test
    public void errorMissingMandatoryParameters() throws Exception {
        MvcResult result = mockMvc.perform(get("/bad-request/missing-mandatory-params")
                .param("params1", "Test")
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("responseCode").value("02"))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        ApiErrorResponse apiErrorResponse = objectMapper.readValue(jsonResult, ApiErrorResponse.class);
        ObjectMapper prettyPrinter = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        String prettyJson = prettyPrinter.writeValueAsString(apiErrorResponse);
        System.out.println(prettyJson);
    }
}
