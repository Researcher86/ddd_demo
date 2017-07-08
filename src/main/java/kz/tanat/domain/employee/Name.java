package kz.tanat.domain.employee;

import java.util.Objects;

/**
 * Объект значение, имя сотрудника.
 *
 * @author Tanat
 * @version 1.0
 * @since 07.07.2017.
 */
public class Name {
    private String last;
    private String first;
    private String middle;

    public Name(String last, String first, String middle) {
        Objects.nonNull(last);
        Objects.nonNull(first);

        this.last = last;
        this.first = first;
        this.middle = middle;
    }

    public String getFull() {
        return String.join(" ", last, first, middle);
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
}
