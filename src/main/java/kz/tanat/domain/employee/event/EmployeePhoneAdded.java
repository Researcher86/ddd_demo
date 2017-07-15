package kz.tanat.domain.employee.event;

import kz.tanat.domain.DomainEvent;
import kz.tanat.domain.employee.EmployeeId;
import kz.tanat.domain.employee.Phone;

import java.time.LocalDate;

/**
 * Событие добавления нового номера телефона.
 * Событие, которое генерируется при внесении нового номера телефона в дело сотрудника.
 *
 * @author Tanat
 * @version 1.1
 * @since 07.07.2017.
 */
public class EmployeePhoneAdded implements DomainEvent {
    private EmployeeId id;
    private Phone phone;
    private int eventVersion;
    private LocalDate occurredOn;

    private EmployeePhoneAdded() {
    }

    public EmployeePhoneAdded(EmployeeId id, Phone phone) {
        this.id = id;
        this.phone = phone;

        this.eventVersion = 1;
        this.occurredOn = LocalDate.now();
    }

    public EmployeeId getId() {
        return id;
    }

    public Phone getPhone() {
        return phone;
    }

    @Override
    public int eventVersion() {
        return eventVersion;
    }

    @Override
    public LocalDate occurredOn() {
        return occurredOn;
    }
}
