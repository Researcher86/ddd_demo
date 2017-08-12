package kz.tanat.domain.employee.event;

import kz.tanat.domain.DomainEvent;
import kz.tanat.domain.employee.EmployeeId;

import java.time.LocalDate;

/**
 * Событие архивация.
 * Событие, которое генерируется при переносе дела сотрудника в архив.
 *
 * @author Tanat
 * @since 07.07.2017.
 */
public class EmployeeArchived implements DomainEvent {
	private EmployeeId id;
	private LocalDate date;
	private int eventVersion;
	private LocalDate occurredOn;

	private EmployeeArchived() {
	}

	public EmployeeArchived(EmployeeId id, LocalDate date) {
		this.id = id;
		this.date = date;
		this.eventVersion = 1;
		this.occurredOn = LocalDate.now();
	}

	public EmployeeId getId() {
		return id;
	}

	public LocalDate getDate() {
		return date;
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
