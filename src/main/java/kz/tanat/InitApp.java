package kz.tanat;

import kz.tanat.app.employee.EmployeeService;
import kz.tanat.app.employee.dto.AddressDto;
import kz.tanat.app.employee.dto.EmployeeDto;
import kz.tanat.app.employee.dto.NameDto;
import kz.tanat.app.employee.dto.PhoneDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.UUID;

/**
 * Класс инициализирует приложение тестовыми данными.
 *
 * @author Tanat
 * @since 12.08.2017.
 */
@Component
public class InitApp implements CommandLineRunner {
	@Autowired
	private EmployeeService employeeService;

	@Override
	public void run(String... strings) throws Exception {
		employeeService.create(new EmployeeDto(
				new UUID(0, 1).toString(),
				new NameDto("Пупкин", "Василий", "Петрович"),
				new AddressDto("Россия", "Липецкая обл.", "г. Пушкин", "ул. Ленина", "25"),
				Arrays.asList(
						new PhoneDto(7, "000", "00000000"),
						new PhoneDto(7, "000", "00000001")
				)
		));

		employeeService.create(new EmployeeDto(
				new UUID(0, 2).toString(),
				new NameDto("Пупкин 2", "Василий", "Петрович"),
				new AddressDto("Россия", "Липецкая обл.", "г. Пушкин", "ул. Ленина", "25"),
				Arrays.asList(new PhoneDto(7, "000", "00000000"))
		));

		employeeService.create(new EmployeeDto(
				new UUID(0, 3).toString(),
				new NameDto("Пупкин 3", "Василий", "Петрович"),
				new AddressDto("Россия", "Липецкая обл.", "г. Пушкин", "ул. Ленина", "25"),
				Arrays.asList(new PhoneDto(7, "000", "00000000"))
		));
	}
}
