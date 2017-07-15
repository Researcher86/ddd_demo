package kz.tanat.domain.employee;

import javax.persistence.Embeddable;
import java.util.Objects;

/**
 * Объект значение, имя сотрудника.
 *
 * @author Tanat
 * @since 07.07.2017.
 */
@Embeddable
public class Name {
    private String last;
    private String first;
    private String middle;

    private Name() {
    }

    public Name(String last, String first, String middle) {
        Objects.nonNull(last);
        Objects.nonNull(first);

        this.last = last;
        this.first = first;
        this.middle = middle;
    }

    public String getLast() {
        return last;
    }

    public String getFirst() {
        return first;
    }

    public String getMiddle() {
        return middle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Name name = (Name) o;

        if (last != null ? !last.equals(name.last) : name.last != null) return false;
        if (first != null ? !first.equals(name.first) : name.first != null) return false;
        return middle != null ? middle.equals(name.middle) : name.middle == null;
    }

    @Override
    public int hashCode() {
        int result = last != null ? last.hashCode() : 0;
        result = 31 * result + (first != null ? first.hashCode() : 0);
        result = 31 * result + (middle != null ? middle.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.join(" ", last, first, middle);
    }
}
