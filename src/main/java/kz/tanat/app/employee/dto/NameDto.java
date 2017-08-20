package kz.tanat.app.employee.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel("Name")
public class NameDto {
    @ApiModelProperty(notes = "Фамилия")
    private String last;
    @ApiModelProperty(notes = "Имя")
    private String first;
    @ApiModelProperty(notes = "Отчество")
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
