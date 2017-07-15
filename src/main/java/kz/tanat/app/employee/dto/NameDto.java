package kz.tanat.app.employee.dto;

import kz.tanat.domain.employee.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Объект хранит информацию об имени сотрудника.
 *
 * @author Tanat
 * @since 09.07.2017.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NameDto {
    private String last;
    private String first;
    private String middle;

    public NameDto(Name name) {
        this.last = name.getLast();
        this.first = name.getFirst();
        this.middle = name.getMiddle();
    }

    public Name createName() {
        return new Name(last, first, middle);
    }
}
