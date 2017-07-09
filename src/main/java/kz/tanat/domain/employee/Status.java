package kz.tanat.domain.employee;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Статус сотрудника в системе.
 *
 * @author Tanat
 * @version 1.1
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Status status = (Status) o;

        if (value != status.value) return false;
        return date != null ? date.equals(status.date) : status.date == null;
    }

    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
