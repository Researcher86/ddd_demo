package kz.tanat.app.employee;

import kz.tanat.app.employee.dto.AddressDto;
import kz.tanat.app.employee.dto.EmployeeDto;
import kz.tanat.app.employee.dto.NameDto;
import kz.tanat.app.employee.dto.PhoneDto;
import kz.tanat.domain.DomainEvent;
import kz.tanat.domain.DomainEventPublisher;
import kz.tanat.domain.DomainEventSubscriber;
import kz.tanat.domain.employee.Employee;
import kz.tanat.domain.employee.EmployeeId;
import kz.tanat.domain.employee.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Реализация сервиса по работе с агрегатом/сущностью сотрудник.
 *
 * @author Tanat
 * @version 1.1
 * @since 09.07.2017.
 */
@Slf4j
public class DefaultEmployeeService implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    public DefaultEmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;

        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<DomainEvent>() {
            @Override
            public void handleEvent(DomainEvent domainEvent) {
                log.info("Event: {}", domainEvent.getClass().getSimpleName());
            }

            @Override
            public Class<DomainEvent> subscribedToEventType() {
                return DomainEvent.class;
            }
        });
    }

    @Override
    public EmployeeDto get(String employeeId) {
        Employee employee = employeeRepository.getOne(new EmployeeId(UUID.fromString(employeeId)));
        return new EmployeeDto(employee);
    }

    @Override
    public void create(EmployeeDto dto) {
        Employee employee = dto.createEmployee(employeeRepository.nextId());

        employeeRepository.save(employee);
    }

    @Override
    public void rename(String employeeId, NameDto dto) {
        Employee employee = employeeRepository.getOne(new EmployeeId(UUID.fromString(employeeId)));
        employee.rename(dto.createName());

        employeeRepository.save(employee);
    }

    @Override
    public void changeAddress(String employeeId, AddressDto dto) {
        Employee employee = employeeRepository.getOne(new EmployeeId(UUID.fromString(employeeId)));
        employee.changeAddress(dto.createAddress());

        employeeRepository.save(employee);
    }

    @Override
    public void addPhone(String employeeId, PhoneDto dto) {
        Employee employee = employeeRepository.getOne(new EmployeeId(UUID.fromString(employeeId)));
        employee.addPhone(dto.createPhone());

        employeeRepository.save(employee);
    }

    @Override
    public void removePhone(String employeeId, int index) {
        Employee employee = employeeRepository.getOne(new EmployeeId(UUID.fromString(employeeId)));
        employee.removePhone(index);
        employeeRepository.save(employee);
    }

    @Override
    public void archive(String employeeId, LocalDate date) {
        Employee employee = employeeRepository.getOne(new EmployeeId(UUID.fromString(employeeId)));
        employee.archive(date);
        employeeRepository.save(employee);
    }

    @Override
    public void reinstate(String employeeId, LocalDate date) {
        Employee employee = employeeRepository.getOne(new EmployeeId(UUID.fromString(employeeId)));
        employee.reinstate(date);
        employeeRepository.save(employee);
    }

    @Override
    public void remove(String employeeId) {
        Employee employee = employeeRepository.getOne(new EmployeeId(UUID.fromString(employeeId)));
        employee.remove();
        employeeRepository.delete(employee);
    }
}
