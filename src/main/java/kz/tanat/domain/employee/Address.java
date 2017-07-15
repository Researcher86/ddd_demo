package kz.tanat.domain.employee;

import javax.persistence.Embeddable;
import java.util.Objects;

/**
 * Объект значение для хранения информации о адресе сотрудника.
 *
 * @author Tanat
 * @since 07.07.2017.
 */
@Embeddable
public class Address {
    private String country;
    private String region;
    private String city;
    private String street;
    private String house;

    private Address() {
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (country != null ? !country.equals(address.country) : address.country != null) return false;
        if (region != null ? !region.equals(address.region) : address.region != null) return false;
        if (city != null ? !city.equals(address.city) : address.city != null) return false;
        if (street != null ? !street.equals(address.street) : address.street != null) return false;
        return house != null ? house.equals(address.house) : address.house == null;
    }

    @Override
    public int hashCode() {
        int result = country != null ? country.hashCode() : 0;
        result = 31 * result + (region != null ? region.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (house != null ? house.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.join(", ", country, region, city, street, house);
    }
}
