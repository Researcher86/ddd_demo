package kz.tanat.domain.employee;

import java.util.Objects;

/**
 * Объект значение, телефон сотрудника.
 *
 * @author Tanat
 * @version 1.0
 * @since 07.07.2017.
 */
public class Phone {
    private int country;
    private String code;
    private String number;

    public Phone(int country, String code, String number) {
        Objects.nonNull(country);
        Objects.nonNull(code);
        Objects.nonNull(number);

        this.country = country;
        this.code = code;
        this.number = number;
    }

    public String getFull() {
        return '+' + country + " (" + code + ") " + number;
    }

    public int getCountry() {
        return country;
    }

    public String getCode() {
        return code;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Phone phone = (Phone) o;

        if (country != phone.country) return false;
        if (code != null ? !code.equals(phone.code) : phone.code != null) return false;
        return number != null ? number.equals(phone.number) : phone.number == null;
    }

    @Override
    public int hashCode() {
        int result = country;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (number != null ? number.hashCode() : 0);
        return result;
    }
}
