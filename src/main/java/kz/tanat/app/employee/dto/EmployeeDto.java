package kz.tanat.app.employee.dto;

import kz.tanat.domain.employee.Employee;
import kz.tanat.domain.employee.EmployeeId;
import kz.tanat.domain.employee.Phones;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Объект хранит информацию об сотруднике для добавления в систему.
 *
 * @author Tanat
 * @version 1.1
 * @since 09.07.2017.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private NameDto name;
    private AddressDto address;
    private List<PhoneDto> phones;

    public EmployeeDto(Employee employee) {
        this.name = new NameDto(employee.getName());
        this.address = new AddressDto(employee.getAddress());
        this.phones = employee.getPhones().getAll().stream().map(PhoneDto::new).collect(Collectors.toList());
    }

    public Employee createEmployee(EmployeeId employeeId) {
        return new Employee(
                employeeId,
                name.createName(),
                address.createAddress(),
                new Phones(
                        phones.stream()
                                .map(PhoneDto::createPhone)
                                .collect(Collectors.toList())
                )
        );
    }
}
