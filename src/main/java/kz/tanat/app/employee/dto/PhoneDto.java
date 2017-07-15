package kz.tanat.app.employee.dto;

import kz.tanat.domain.employee.Phone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Объект хранит информацию об номере телефона сотрудника.
 *
 * @author Tanat
 * @since 09.07.2017.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneDto {
    private int country;
    private String code;
    private String number;

    public PhoneDto(Phone phone) {
        this.country = phone.getCountry();
        this.code = phone.getCode();
        this.number = phone.getNumber();
    }

    public Phone createPhone() {
        return new Phone(country, code, number);
    }
}
