package kz.tanat.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Объект хранит информацию об сотруднике для добавления в систему.
 *
 * @author Tanat
 * @version 1.0
 * @since 09.07.2017.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private NameDto name;
    private AddressDto address;
    private List<PhoneDto> phones;
}
