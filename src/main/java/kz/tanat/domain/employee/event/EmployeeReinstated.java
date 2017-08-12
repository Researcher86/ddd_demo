package kz.tanat.domain.employee.event;

import kz.tanat.domain.DomainEvent;
import kz.tanat.domain.employee.EmployeeId;

import java.time.LocalDate;

/**
 * Событие разархивация.
 * Событие, которое генерируется при восстановлении дела сотрудника из архива.
 *
 * @author Tanat
 * @since 07.07.2017.
 */
public class EmployeeReinstated implements DomainEvent {
	private EmployeeId id;
	private LocalDate date;
	private int eventVersion;
	private LocalDate occurredOn;

	private EmployeeReinstated() {
	}

	public EmployeeReinstated(EmployeeId id, LocalDate date) {
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
