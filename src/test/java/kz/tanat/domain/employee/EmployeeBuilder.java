package kz.tanat.domain.employee;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

/**
 * Билдер для создания тестового сотрудника.
 *
 * @author Tanat
 * @version 1.0
 * @since 07.07.2017.
 */
public class EmployeeBuilder {
    private UUID id = new UUID(0L, 1L);
    private Phones phones;
    private boolean archived = false;

    private EmployeeBuilder() {
        phones = new Phones(Arrays.asList(new Phone(7, "000", "00000000")));
    }

    public static EmployeeBuilder instance() {
        return new EmployeeBuilder();
    }

    public EmployeeBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    public EmployeeBuilder withPhones(Phones phones) {
        this.phones = phones;
        return this;
    }

    public EmployeeBuilder archived() {
        this.archived = true;
        return this;
    }

    public Employee build() {
        Employee employee = new Employee(
                new EmployeeId(this.id),
                new Name("Пупкин", "Василий", "Петрович"),
                new Address("Россия", "Липецкая обл.", "г. Пушкин", "ул. Ленина", "25"),
                this.phones
        );
        if (this.archived) {
            employee.archive(LocalDate.now());
        }
        return employee;
    }
}
