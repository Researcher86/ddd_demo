package kz.tanat.domain.employee.event;

import kz.tanat.domain.DomainEvent;
import kz.tanat.domain.employee.Address;
import kz.tanat.domain.employee.EmployeeId;

import java.time.LocalDate;

/**
 * Событие изменение адреса.
 * Событие, которое генерируется при изменении адреса сотрудника.
 *
 * @author Tanat
 * @since 07.07.2017.
 */
public class EmployeeAddressChanged implements DomainEvent {
    private EmployeeId id;
    private Address address;
    private int eventVersion;
    private LocalDate occurredOn;

    private EmployeeAddressChanged() {
    }

    public EmployeeAddressChanged(EmployeeId id, Address address) {
        this.id = id;
        this.address = address;

        this.eventVersion = 1;
        this.occurredOn = LocalDate.now();
    }

    public EmployeeId getId() {
        return id;
    }

    public Address getAddress() {
        return address;
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
