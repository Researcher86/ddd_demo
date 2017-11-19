package kz.tanat.web.system.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.tanat.web.helper.EmployeeFullName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Тестируем реализацию Web контроллера.
 *
 * @author Tanat
 * @since 25.10.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void list() throws Exception {
        mockMvc.perform(get("/employees"))
                .andDo(print())
                .andExpect(
                        status().isOk()
                );
    }

    @Test
    public void showSuccess() throws Exception {
        mockMvc.perform(get("/employees/{id}", UUID.randomUUID()))
                .andDo(print())
                .andExpect(
                        status().isOk()
                );
    }

    @Test
    public void showNotFound() throws Exception {
        mockMvc.perform(get("/employees/{id}", UUID.randomUUID()))
                .andDo(print())
                .andExpect(
                        status().isNotFound()
                );
    }

}