package kz.tanat;

import kz.tanat.domain.DomainEvent;
import kz.tanat.domain.employee.*;
import kz.tanat.domain.event.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.UUID;

import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

/**
 * Класс для запуска системы.
 *
 * @author Tanat
 * @since 07.07.2017.
 */
@Slf4j
@SpringBootApplication
@EnableSwagger2
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Docket newsApi() {
        return new Docket(SWAGGER_2)
                .groupName("employees")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("kz.tanat.web"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Employee REST API")
                .contact(new Contact("Alpenov Tanat",
                        "https://github.com/Batiaev/demo-quotation-webservice",
                        "researcher2286@gmail.com"))
                .version("1.0")
                .build();
    }

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EventRepository eventRepository;

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

            eventRepository.findAll().forEach(storedEvent -> {
                DomainEvent domainEvent = storedEvent.toDomainEvent();
                System.out.println(domainEvent);
            });
        };
    }

}
