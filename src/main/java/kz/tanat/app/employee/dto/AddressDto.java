package kz.tanat.app.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Объект хранит информацию об адресе проживания.
 *
 * @author Tanat
 * @version 1.0
 * @since 09.07.2017.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    private String country;
    private String region;
    private String city;
    private String street;
    private String house;
}
