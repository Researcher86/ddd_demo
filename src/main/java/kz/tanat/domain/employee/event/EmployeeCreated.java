package kz.tanat.domain.employee.event;

import kz.tanat.domain.DomainEvent;
import kz.tanat.domain.employee.EmployeeId;

import java.time.LocalDate;

/**
 * Событие добавления нового сотрудника в систему.
 * Событие, которое генерируется при внесении нового дела сотрудника в систему.
 *
 * @author Tanat
 * @version 1.0
 * @since 07.07.2017.
 */
public class EmployeeCreated implements DomainEvent {
    private final EmployeeId id;
    private final int eventVersion;
    private final LocalDate occurredOn;

    public EmployeeCreated(EmployeeId id) {
        this.id = id;
        this.eventVersion = 1;
        this.occurredOn = LocalDate.now();
    }

    public EmployeeId getId() {
        return id;
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
