package kz.tanat.domain.employee.event;

import kz.tanat.domain.DomainEvent;
import kz.tanat.domain.employee.EmployeeId;
import kz.tanat.domain.employee.Name;

import java.time.LocalDate;

/**
 * Событие переименование.
 * Событие, которое генерируется при изменении имени сотрудника.
 *
 * @author Tanat
 * @version 1.0
 * @since 07.07.2017.
 */
public class EmployeeRenamed implements DomainEvent {
    private final EmployeeId id;
    private final Name name;
    private final int eventVersion;
    private final LocalDate occurredOn;

    public EmployeeRenamed(EmployeeId id, Name name) {
        this.id = id;
        this.name = name;

        this.eventVersion = 1;
        this.occurredOn = LocalDate.now();
    }

    public EmployeeId getId() {
        return id;
    }

    public Name getName() {
        return name;
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
