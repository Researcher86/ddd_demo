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
 * @version 1.0
 * @since 07.07.2017.
 */
public class EmployeeAddressChanged implements DomainEvent {
    private final EmployeeId id;
    private final Address address;
    private final int eventVersion;
    private final LocalDate occurredOn;

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
