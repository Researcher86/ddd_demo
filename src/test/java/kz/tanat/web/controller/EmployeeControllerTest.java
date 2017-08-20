package kz.tanat.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.tanat.app.employee.EmployeeService;
import kz.tanat.app.employee.dto.EmployeeDto;
import kz.tanat.domain.employee.EmployeeBuilder;
import kz.tanat.domain.employee.EmployeeRepository;
import kz.tanat.domain.event.EventRepository;
import kz.tanat.web.helper.EmployeeFullName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Тестируем реализацию Web контроллера.
 *
 * @author Tanat
 * @since 15.07.2017.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {
    @MockBean
    private EventRepository eventRepository;
    @MockBean
    private EmployeeRepository employeeRepository;
    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @TestConfiguration
    static class Config {

        @Bean
        public EmployeeFullName employeeFullName() {
            return new EmployeeFullName();
        }

    }

    @Test
    public void list() throws Exception {
        given(employeeService.getAll()).willReturn(Arrays.asList(new EmployeeDto(EmployeeBuilder.instance().build())));
        mockMvc.perform(get("/employees"))
                .andDo(print())
                .andExpect(
                        status().isOk()
                );
    }

    @Test
    public void showSuccess() throws Exception {
        given(employeeService.get(any())).willReturn(new EmployeeDto(EmployeeBuilder.instance().build()));
        mockMvc.perform(get("/employees/{id}", UUID.randomUUID()))
                .andDo(print())
                .andExpect(
                        status().isOk()
                );
    }

    @Test
    public void showNotFound() throws Exception {
        given(employeeService.get(any())).willThrow(new IllegalArgumentException("Employee not found."));
        mockMvc.perform(get("/employees/{id}", UUID.randomUUID()))
                .andDo(print())
                .andExpect(
                        status().isNotFound()
                );
    }

}