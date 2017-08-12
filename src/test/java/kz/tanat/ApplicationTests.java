package kz.tanat;

import kz.tanat.app.employee.dto.EmployeeDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.UUID;

/**
 * Интеграционные тесты всей системы.
 *
 * @author Tanat
 * @since 07.07.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTests {
	@Autowired
	private TestRestTemplate template;

	@Test
	public void getEmployees() throws Exception {
		ResponseEntity<EmployeeDto[]> response = template.getForEntity("/api/employees", EmployeeDto[].class);
		System.out.println(Arrays.toString(response.getBody()));

//        assertThat(response.getBody(), equalTo("Greetings from Spring Boot!"));
	}

	@Test
	public void getEmployee() throws Exception {
		ResponseEntity<EmployeeDto> response = template.getForEntity("/api/employees/{id}", EmployeeDto.class, new UUID(0L, 2L));
		System.out.println(response.getBody());

//        assertThat(response.getBody(), equalTo("Greetings from Spring Boot!"));
	}
}
