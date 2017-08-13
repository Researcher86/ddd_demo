package kz.tanat.app.employee.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.tanat.domain.employee.Employee;
import kz.tanat.domain.employee.EmployeeId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Объект хранит информацию об сотруднике для добавления в систему.
 *
 * @author Tanat
 * @since 09.07.2017.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("Employee")
public class EmployeeDto {
	@ApiModelProperty(notes = "Идентификатор сотрудника")
	private String id;
	@ApiModelProperty(notes = "Имя сотрудника")
	private NameDto name;
	@ApiModelProperty(notes = "Адрес сорудника")
	private AddressDto address;
	@ApiModelProperty(notes = "Телефоны сотрудника")
	private List<PhoneDto> phones;

	public EmployeeDto(Employee employee) {
		this.id = employee.getId().toString();
		this.name = new NameDto(employee.getName());
		this.address = new AddressDto(employee.getAddress());
		this.phones = employee.getPhones().stream().map(PhoneDto::new).collect(Collectors.toList());
	}

	public Employee createEmployee(EmployeeId employeeId) {
		return new Employee(
				id == null ? employeeId : new EmployeeId(UUID.fromString(id)),
				name.createName(),
				address.createAddress(),
				phones.stream().map(PhoneDto::createPhone).collect(Collectors.toList())
		);
	}

}
