package kz.tanat.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.tanat.app.employee.EmployeeService;
import kz.tanat.app.employee.dto.EmployeeDto;
import kz.tanat.domain.employee.EmployeeBuilder;
import kz.tanat.domain.employee.EmployeeRepository;
import kz.tanat.domain.event.EventRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Тестируем реализацию Web контроллера.
 *
 * @author Tanat
 * @version 1.0
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

    @Before
    public void setUp() throws Exception {
        when(employeeService.getAll()).thenReturn(Arrays.asList(new EmployeeDto(EmployeeBuilder.instance().build())));
        when(employeeService.get(any())).thenReturn(new EmployeeDto(EmployeeBuilder.instance().build()));
    }

    @Test
    public void list() throws Exception {
        mockMvc.perform(get("/employees"))
                .andDo(print())
                .andExpect(
                        status().isOk()
                );
    }

    @Test
    public void show() throws Exception {
        mockMvc.perform(get("/employees/{id}", UUID.randomUUID()))
                .andDo(print())
                .andExpect(
                        status().isOk()
                );
    }

}