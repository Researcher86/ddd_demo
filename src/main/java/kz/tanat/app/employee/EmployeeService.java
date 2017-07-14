package kz.tanat.app.employee;

import kz.tanat.app.employee.dto.AddressDto;
import kz.tanat.app.employee.dto.EmployeeDto;
import kz.tanat.app.employee.dto.NameDto;
import kz.tanat.app.employee.dto.PhoneDto;

import java.time.LocalDate;
import java.util.List;

/**
 * Интерфейс сервиса для реализации логики по работе с агрегатом/сущностью сотрудник.
 *
 * @author Tanat
 * @version 1.0
 * @since 09.07.2017.
 */
public interface EmployeeService {
    EmployeeDto get(String employeeId);

    List<EmployeeDto> getAll();

    void create(EmployeeDto dto);

    void rename(String employeeId, NameDto dto);

    void changeAddress(String employeeId, AddressDto dto);

    void addPhone(String employeeId, PhoneDto dto);

    void removePhone(String employeeId, int index);

    void archive(String employeeId, LocalDate date);

    void reinstate(String employeeId, LocalDate date);

    void remove(String employeeId);
}
