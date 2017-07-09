package kz.tanat.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Объект хранит информацию об имени сотрудника.
 *
 * @author Tanat
 * @version 1.0
 * @since 09.07.2017.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NameDto {
    private String last;
    private String first;
    private String middle;
}
