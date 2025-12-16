package test.com.work.controllers;

import com.work.SpringProjApplicationStart;
import com.work.controllers.FailoverRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FailoverRestController.class)
@ContextConfiguration(classes = SpringProjApplicationStart.class)
public class FailoverRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnUserById() throws Exception {
        // Act & Assert: Perform the GET request and check the result
        mockMvc.perform(get("/api/failover/getAllEmployees", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.status").value("Success"))
                // 1. Check the total number of items in the list (Optional)
                .andExpect(jsonPath("$.listData").isArray())
                .andExpect(jsonPath("$.listData.size()").value(1000))
                // 2. Loop and Match (Crucial Step):
                // $[*] iterates through all elements in the array.
                // [?(@.id == 42)] filters the array elements where the 'id' field is 42.
                // [0] selects the first match from the filtered result.
                // .name checks the 'name' field of that matched element.
                //.andExpect(jsonPath("$[*][?(@.id == 42)].name").value("Special Widget"));
                .andExpect(jsonPath("$[*][?(@.employeeId == 42)][0].firstName").value("Zechariah"));
    }
}
