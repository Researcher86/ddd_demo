package kz.tanat.app.employee.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kz.tanat.domain.employee.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Объект хранит информацию об адресе проживания.
 *
 * @author Tanat
 * @since 09.07.2017.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("Address")
public class AddressDto {
    @ApiModelProperty(notes = "Страна")
    private String country;
    @ApiModelProperty(notes = "Регион")
    private String region;
    @ApiModelProperty(notes = "Город")
    private String city;
    @ApiModelProperty(notes = "Улица")
    private String street;
    @ApiModelProperty(notes = "Дом")
    private String house;

    public AddressDto(Address address) {
        this.country = address.getCountry();
        this.region = address.getRegion();
        this.city = address.getCity();
        this.street = address.getStreet();
        this.house = address.getHouse();
    }

    public Address createAddress() {
        return new Address(country, region, city, street, house);
    }
}
