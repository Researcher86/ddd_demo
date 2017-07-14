package kz.tanat;

import kz.tanat.app.employee.dto.AddressDto;
import kz.tanat.app.employee.dto.EmployeeDto;
import kz.tanat.app.employee.dto.NameDto;
import kz.tanat.app.employee.dto.PhoneDto;
import kz.tanat.domain.employee.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.UUID;

/**
 * Класс для запуска системы.
 *
 * @author Tanat
 * @version 1.0
 * @since 07.07.2017.
 */
@Slf4j
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private EmployeeRepository employeeRepository;

    @Bean
    CommandLineRunner runner() {
        return strings -> {

            employeeRepository.save(new Employee(
                    new EmployeeId(new UUID(0L, 1L)),
                    new Name("Пупкин", "Василий", "Петрович"),
                    new Address("Россия", "Липецкая обл.", "г. Пушкин", "ул. Ленина", "25"),
                    Arrays.asList(new Phone(7, "000", "00000000"))
            ));

            employeeRepository.save(new Employee(
                    new EmployeeId(new UUID(0L, 2L)),
                    new Name("Пупкин 2", "Василий", "Петрович"),
                    new Address("Россия", "Липецкая обл.", "г. Пушкин", "ул. Ленина", "25"),
                    Arrays.asList(new Phone(7, "000", "00000000"))
            ));

            employeeRepository.save(new Employee(
                    new EmployeeId(new UUID(0L, 3L)),
                    new Name("Пупкин 3", "Василий", "Петрович"),
                    new Address("Россия", "Липецкая обл.", "г. Пушкин", "ул. Ленина", "25"),
                    Arrays.asList(new Phone(7, "000", "00000000"))
            ));
        };
    }

}
