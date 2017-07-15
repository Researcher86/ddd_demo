package kz.tanat.domain.employee;

import kz.tanat.domain.AbstractEntity;
import kz.tanat.domain.DomainEventPublisher;
import kz.tanat.domain.DomainException;
import kz.tanat.domain.employee.event.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Агрегат (Сущность) сотрудник.
 *
 * @author Tanat
 * @since 07.07.2017.
 */
@Entity
public class Employee extends AbstractEntity {
    @EmbeddedId
    private EmployeeId id;
    @Embedded
    private Name name;
    @Embedded
    private Address address;
    @Embedded
    private Phones phones;
    private LocalDate createDate;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Status> status = new ArrayList<>();

    private Employee() {
    }

    public Employee(EmployeeId id, Name name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = new Phones(phones);
        this.createDate = LocalDate.now();
        this.status.add(new Status(Status.State.ACTIVE, createDate));

        DomainEventPublisher.instance().publish(new EmployeeCreated(id));
    }

    public void rename(Name name) {
        this.name = name;
        DomainEventPublisher.instance().publish(new EmployeeRenamed(this.id, name));
    }

    public void changeAddress(Address address) {
        this.address = address;
        DomainEventPublisher.instance().publish(new EmployeeAddressChanged(this.id, address));
    }

    public void addPhone(Phone phone) {
        this.phones.add(phone);
        DomainEventPublisher.instance().publish(new EmployeePhoneAdded(this.id, phone));
    }

    public void removePhone(int index) {
        Phone phone = this.phones.remove(index);
        DomainEventPublisher.instance().publish(new EmployeePhoneRemoved(this.id, phone));
    }

    public boolean containsPhone(Phone phone) {
        return this.phones.contains(phone);
    }

    public void archive(LocalDate date) {
        if (isArchived()) {
            throw new DomainException("Employee is already archived.");
        }

        this.status.add(new Status(Status.State.ARCHIVED, date));
        DomainEventPublisher.instance().publish(new EmployeeArchived(this.id, date));
    }

    public void reinstate(LocalDate date) {
        if (isActive()) {
            throw new DomainException("Employee is not archived.");
        }

        this.status.add(new Status(Status.State.ACTIVE, date));
        DomainEventPublisher.instance().publish(new EmployeeReinstated(this.id, date));
    }

    public void remove() {
        if (isActive()) {
            throw new DomainException("Cannot remove active employee.");
        }

        DomainEventPublisher.instance().publish(new EmployeeRemoved(this.id));
    }

    public boolean isActive() {
        return getCurrentStatus().isActive();
    }

    public boolean isArchived() {
        return getCurrentStatus().isArchived();
    }

    public Status getCurrentStatus() {
        return this.status.get(this.status.size() - 1);
    }

    public EmployeeId getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public List<Phone> getPhones() {
        return phones.getAll();
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public List<Status> getStatus() {
        return Collections.unmodifiableList(status);
    }
}
