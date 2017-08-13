package kz.tanat.app.employee;

import kz.tanat.app.employee.dto.AddressDto;
import kz.tanat.app.employee.dto.EmployeeDto;
import kz.tanat.app.employee.dto.NameDto;
import kz.tanat.app.employee.dto.PhoneDto;
import kz.tanat.domain.employee.Employee;
import kz.tanat.domain.employee.EmployeeId;
import kz.tanat.domain.employee.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Сервис по работе с агрегатом/сущностью сотрудник.
 *
 * @author Tanat
 * @since 09.07.2017.
 */
@Slf4j
@Service
@Transactional
public class EmployeeService {
	private final EmployeeRepository employeeRepository;

	public EmployeeService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	public EmployeeDto get(String employeeId) {
		Employee employee = getOne(employeeId);
		return new EmployeeDto(employee);
	}

	public List<EmployeeDto> getAll() {
		return employeeRepository.findAll().stream().map(EmployeeDto::new).collect(Collectors.toList());
	}

	public EmployeeDto create(EmployeeDto dto) {
		Employee employee = dto.createEmployee(employeeRepository.nextId());

		employeeRepository.save(employee);

		return new EmployeeDto(employee);
	}

	public void remove(String employeeId) {
		Employee employee = getOne(employeeId);
		employee.remove();
		employeeRepository.delete(employee);
	}

	public void rename(String employeeId, NameDto dto) {
		Employee employee = getOne(employeeId);
		employee.rename(dto.createName());
	}

	public void changeAddress(String employeeId, AddressDto dto) {
		Employee employee = getOne(employeeId);
		employee.changeAddress(dto.createAddress());
	}

	public void addPhone(String employeeId, PhoneDto dto) {
		Employee employee = getOne(employeeId);
		employee.addPhone(dto.createPhone());
	}

	public void removePhone(String employeeId, int index) {
		Employee employee = getOne(employeeId);
		employee.removePhone(index);
	}

	public void archive(String employeeId, LocalDate date) {
		Employee employee = getOne(employeeId);
		employee.archive(date);
	}

	public void reinstate(String employeeId, LocalDate date) {
		Employee employee = getOne(employeeId);
		employee.reinstate(date);
	}

	private Employee getOne(String employeeId) {
		Employee employee = employeeRepository.findOne(new EmployeeId(UUID.fromString(employeeId)));

		if (employee == null) {
			throw new IllegalArgumentException("Employee not found.");
		}

		return employee;
	}
}
