package kz.tanat.domain;

import java.util.UUID;

/**
 * Базовый класс идентификации сущностей в системе.
 *
 * @author Tanat
 * @version 1.1
 * @since 07.07.2017.
 */
public abstract class AbstractId {
    private UUID id;

    public AbstractId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractId that = (AbstractId) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
