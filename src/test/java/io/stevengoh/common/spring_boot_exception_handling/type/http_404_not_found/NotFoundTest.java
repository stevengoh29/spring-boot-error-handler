package io.stevengoh.common.spring_boot_exception_handling.type.http_404_not_found;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = NotFoundTestRestController.class)
public class NotFoundTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

//    @Test
    public void ResourceNotFoundTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/not-found/no-resource-endpoint"))
                .andExpect(status().isNotFound())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());

//        String jsonResult = result.getResponse().getContentAsString();
//        ApiErrorResponse apiErrorResponse = objectMapper.readValue(jsonResult, ApiErrorResponse.class);
//        ObjectMapper prettyPrinter = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
//        String prettyJson = prettyPrinter.writeValueAsString(apiErrorResponse);
//        System.out.println(prettyJson);
    }
}
