package kz.tanat.domain.employee;

import kz.tanat.domain.DomainException;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Класс для работы со список телефонов сотрудника.
 *
 * @author Tanat
 * @version 1.3
 * @since 07.07.2017.
 */
@Embeddable
class Phones {
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Phone> phones = new ArrayList<>();

    private Phones() {
    }

    Phones(List<Phone> phones) {
        if (phones == null || phones.isEmpty()) {
            throw new DomainException("Employee must contain at least one phone.");
        }

        phones.forEach(this::add);
    }

    void add(Phone phone) {
        if (phones.stream().anyMatch(p -> p.equals(phone))) {
            throw new DomainException("Phone already exists.");
        }

        phones.add(phone);
    }

    Phone remove(int index) {
        if (index > phones.size()) {
            throw new DomainException("Phone not found.");
        }

        if (phones.size() == 1) {
            throw new DomainException("Cannot remove the last phone.");
        }

        return phones.remove(index);
    }

    boolean contains(Phone phone) {
        return this.phones.contains(phone);
    }

    List<Phone> getAll() {
        return Collections.unmodifiableList(phones);
    }
}
