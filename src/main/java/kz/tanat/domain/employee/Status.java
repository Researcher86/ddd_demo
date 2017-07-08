package kz.tanat.domain.employee;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Статус сотрудника в системе.
 *
 * @author Tanat
 * @version 1.0
 * @since 07.07.2017.
 */
public class Status {
    public enum State {
        ACTIVE,
        ARCHIVED
    }

    private State value;
    private LocalDate date;

    public Status(State value, LocalDate date) {
        Objects.nonNull(value);
        Objects.nonNull(date);

        this.value = value;
        this.date = date;
    }

    public boolean isActive() {
        return value == State.ACTIVE;
    }

    public boolean isArchived() {
        return value == State.ARCHIVED;
    }

    public State getValue() {
        return value;
    }

    public LocalDate getDate() {
        return date;
    }
}
