package kz.tanat.domain.employee.event;

import kz.tanat.domain.DomainEvent;
import kz.tanat.domain.employee.EmployeeId;
import kz.tanat.domain.employee.Phone;

import java.time.LocalDate;

/**
 * Событие удаления номера телефона.
 * Событие, которое генерируется при удалении номера телефона из дела сотрудника.
 *
 * @author Tanat
 * @since 07.07.2017.
 */
public class EmployeePhoneRemoved implements DomainEvent {
    private EmployeeId id;
    private Phone phone;
    private int eventVersion;
    private LocalDate occurredOn;

    private EmployeePhoneRemoved() {
    }

    public EmployeePhoneRemoved(EmployeeId id, Phone phone) {
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
