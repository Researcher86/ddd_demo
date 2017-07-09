package kz.tanat.app;

import kz.tanat.app.dto.AddressDto;
import kz.tanat.app.dto.EmployeeDto;
import kz.tanat.app.dto.NameDto;
import kz.tanat.app.dto.PhoneDto;
import kz.tanat.domain.DomainEvent;
import kz.tanat.domain.DomainEventPublisher;
import kz.tanat.domain.DomainEventSubscriber;
import kz.tanat.domain.employee.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.stream.Collectors;

/**
 * Реализация сервиса по работе с агрегатом/сущностью сотрудник.
 *
 * @author Tanat
 * @version 1.0
 * @since 09.07.2017.
 */
@Slf4j
public class DefaultEmployeeService implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    public DefaultEmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;

        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<DomainEvent>() {
            @Override
            public void handleEvent(DomainEvent aDomainEvent) {
                log.info("Event: {}", aDomainEvent.getClass().getSimpleName());
            }

            @Override
            public Class<DomainEvent> subscribedToEventType() {
                return DomainEvent.class;
            }
        });
    }

    @Override
    public EmployeeDto get(String employeeId) {
        Employee employee = employeeRepository.get(new EmployeeId(Long.parseLong(employeeId)));
        return new EmployeeDto(
                new NameDto(
                        employee.getName().getLast(),
                        employee.getName().getFirst(),
                        employee.getName().getMiddle()
                ),
                new AddressDto(
                        employee.getAddress().getCountry(),
                        employee.getAddress().getRegion(),
                        employee.getAddress().getCity(),
                        employee.getAddress().getStreet(),
                        employee.getAddress().getHouse()
                ),
                employee.getPhones().getAll().stream()
                        .map(phone -> new PhoneDto(
                                phone.getCountry(),
                                phone.getCode(),
                                phone.getNumber()))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public void create(EmployeeDto dto) {
        Employee employee = new Employee(
                employeeRepository.nextId(),
                new Name(
                        dto.getName().getLast(),
                        dto.getName().getFirst(),
                        dto.getName().getMiddle()
                ),
                new Address(
                        dto.getAddress().getCountry(),
                        dto.getAddress().getRegion(),
                        dto.getAddress().getCity(),
                        dto.getAddress().getStreet(),
                        dto.getAddress().getHouse()
                ),
                new Phones(
                        dto.getPhones()
                                .stream()
                                .map(phone -> new Phone(
                                        phone.getCountry(),
                                        phone.getCode(),
                                        phone.getNumber()))
                                .collect(Collectors.toList())
                )
        );

        employeeRepository.add(employee);
    }

    @Override
    public void rename(String employeeId, NameDto dto) {
        Employee employee = employeeRepository.get(new EmployeeId(Long.parseLong(employeeId)));
        employee.rename(new Name(
                dto.getLast(),
                dto.getFirst(),
                dto.getMiddle()
        ));

        employeeRepository.save(employee);
    }

    @Override
    public void changeAddress(String employeeId, AddressDto dto) {
        Employee employee = employeeRepository.get(new EmployeeId(Long.parseLong(employeeId)));
        employee.changeAddress(new Address(
                dto.getCountry(),
                dto.getRegion(),
                dto.getCity(),
                dto.getStreet(),
                dto.getHouse()
        ));

        employeeRepository.save(employee);
    }

    @Override
    public void addPhone(String employeeId, PhoneDto dto) {
        Employee employee = employeeRepository.get(new EmployeeId(Long.parseLong(employeeId)));
        employee.addPhone(new Phone(
                dto.getCountry(),
                dto.getCode(),
                dto.getNumber()
        ));

        employeeRepository.save(employee);
    }

    @Override
    public void removePhone(String employeeId, int index) {
        Employee employee = employeeRepository.get(new EmployeeId(Long.parseLong(employeeId)));
        employee.removePhone(index);
        employeeRepository.save(employee);
    }

    @Override
    public void archive(String employeeId, LocalDate date) {
        Employee employee = employeeRepository.get(new EmployeeId(Long.parseLong(employeeId)));
        employee.archive(date);
        employeeRepository.save(employee);
    }

    @Override
    public void reinstate(String employeeId, LocalDate date) {
        Employee employee = employeeRepository.get(new EmployeeId(Long.parseLong(employeeId)));
        employee.reinstate(date);
        employeeRepository.save(employee);
    }

    @Override
    public void remove(String employeeId) {
        Employee employee = employeeRepository.get(new EmployeeId(Long.parseLong(employeeId)));
        employee.remove();
        employeeRepository.remove(employee);
    }
}
