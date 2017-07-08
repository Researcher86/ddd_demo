package kz.tanat.domain.employee;

import java.util.Objects;

/**
 * Объект значение для хранения информации о адресе сотрудника.
 *
 * @author Tanat
 * @version 1.0
 * @since 07.07.2017.
 */
public class Address {
    private String country;
    private String region;
    private String city;
    private String street;
    private String house;

    public Address(String country, String region, String city, String street, String house) {
        Objects.nonNull(country);
        Objects.nonNull(city);

        this.country = country;
        this.region = region;
        this.city = city;
        this.street = street;
        this.house = house;
    }

    public String getCountry() {
        return country;
    }

    public String getRegion() {
        return region;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getHouse() {
        return house;
    }
}
