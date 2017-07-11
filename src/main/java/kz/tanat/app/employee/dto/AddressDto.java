package kz.tanat.app.employee.dto;

import kz.tanat.domain.employee.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Объект хранит информацию об адресе проживания.
 *
 * @author Tanat
 * @version 1.1
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
