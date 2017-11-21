package kz.tanat;

import kz.tanat.app.employee.EmployeeService;
import kz.tanat.app.employee.dto.AddressDto;
import kz.tanat.app.employee.dto.EmployeeDto;
import kz.tanat.app.employee.dto.NameDto;
import kz.tanat.app.employee.dto.PhoneDto;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Класс инициализирует приложение тестовыми данными.
 *
 * @author Tanat
 * @since 12.08.2017.
 */
@Component
@AllArgsConstructor
public class DbSeeder implements CommandLineRunner {
    private final EmployeeService employeeService;

    @Override
    public void run(String... strings) throws Exception {
        Stream.of(new EmployeeDto(
                        new UUID(0, 1).toString(),
                        new NameDto("Пупкин", "Василий", "Петрович"),
                        new AddressDto("Россия", "Липецкая обл.", "г. Пушкин", "ул. Ленина", "25"),
                        Arrays.asList(
                                new PhoneDto(7, "000", "00000000"),
                                new PhoneDto(7, "000", "00000001")
                        )),
                new EmployeeDto(
                        new UUID(0, 2).toString(),
                        new NameDto("Пупкин 2", "Василий", "Петрович"),
                        new AddressDto("Россия", "Липецкая обл.", "г. Пушкин", "ул. Ленина", "25"),
                        Arrays.asList(
                                new PhoneDto(7, "000", "00000000")
                        )),
                new EmployeeDto(
                        new UUID(0, 3).toString(),
                        new NameDto("Пупкин 3", "Василий", "Петрович"),
                        new AddressDto("Россия", "Липецкая обл.", "г. Пушкин", "ул. Ленина", "25"),
                        Arrays.asList(
                                new PhoneDto(7, "000", "00000000")
                        ))
        ).forEach(employeeService::create);
    }
}
