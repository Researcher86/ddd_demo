package kz.tanat.app.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Объект хранит информацию об номере телефона сотрудника.
 *
 * @author Tanat
 * @version 1.0
 * @since 09.07.2017.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneDto {
    private int country;
    private String code;
    private String number;
}
