package kz.tanat.domain.employee;

import kz.tanat.domain.DomainException;
import kz.tanat.domain.EventTracking;
import kz.tanat.domain.employee.event.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Тестируем логику работы агрегата/сущности сотрудников.
 *
 * @author Tanat
 * @version 1.2
 * @since 07.07.2017.
 */
public class EmployeeTest {
    private EventTracking eventTracking;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    private EmployeeId id = new EmployeeId(UUID.randomUUID());
    private Name name = new Name("Пупкин", "Василий", "Петрович");
    private Address address = new Address("Россия", "Липецкая обл.", "г. Пушкин", "ул. Ленина", "25");
    private List<Phone> phones = Arrays.asList(
            new Phone(7, "920", "00000001"),
            new Phone(7, "910", "00000002")
    );

    @Before
    public void setUp() throws Exception {
        eventTracking = new EventTracking();
    }

    @Test
    public void createSuccess() throws Exception {
        Employee employee = new Employee(id, name, address, phones);

        assertEquals(id, employee.getId());
        assertEquals(name, employee.getName());
        assertEquals(address, employee.getAddress());
        assertEquals(phones, employee.getPhones());
        assertNotNull(employee.getCreateDate());
        assertTrue(employee.isActive());
        assertEquals(1, employee.getStatus().size());
        assertTrue(employee.getCurrentStatus().isActive());

        eventTracking.expectedEvents(1);
        eventTracking.expectedEvent(EmployeeCreated.class);
    }

    @Test
    public void createWithoutPhones() throws Exception {
        expectedEx.expectMessage("Employee must contain at least one phone.");
        new Employee(id, name, address, Arrays.asList());
    }

    @Test
    public void createWithSamePhoneNumbers() throws Exception {
        expectedEx.expectMessage("Phone already exists.");
        new Employee(id, name, address, Arrays.asList(
                new Phone(7, "920", "00000001"),
                new Phone(7, "920", "00000001")
        ));
    }

    @Test
    public void archiveSuccess() throws Exception {
        Employee employee = EmployeeBuilder.instance().build();

        employee.archive(LocalDate.of(2011, 6, 15));

        assertFalse(employee.isActive());
        assertTrue(employee.isArchived());
        assertFalse(employee.getStatus().isEmpty());
        assertTrue(employee.getCurrentStatus().isArchived());

        eventTracking.expectedEvents(2);
        eventTracking.expectedLastEvent(EmployeeArchived.class);
    }

    @Test
    public void archiveAlreadyArchived() throws Exception {
        Employee employee = EmployeeBuilder.instance().archived().build();

        expectedEx.expect(DomainException.class);
        expectedEx.expectMessage("Employee is already archived.");

        employee.archive(LocalDate.of(2011, 6, 15));
    }

    @Test
    public void changeAddressSuccess() throws Exception {
        Employee employee = EmployeeBuilder.instance().build();
        Address address = new Address("New", "Test", "Address", "Street", "25a");

        employee.changeAddress(address);
        assertEquals(address, employee.getAddress());

        eventTracking.expectedEvents(2);
        eventTracking.expectedLastEvent(EmployeeAddressChanged.class);
    }

    @Test
    public void reinstateSuccess() throws Exception {
        Employee employee = EmployeeBuilder.instance().archived().build();
        assertFalse(employee.isActive());
        assertTrue(employee.isArchived());

        employee.reinstate(LocalDate.of(2011, 6, 15));

        assertTrue(employee.isActive());
        assertFalse(employee.isArchived());
        assertTrue(employee.getCurrentStatus().isActive());

        eventTracking.expectedLastEvent(EmployeeReinstated.class);
    }

    @Test
    public void reinstateNotArchived() throws Exception {
        Employee employee = EmployeeBuilder.instance().build();

        expectedEx.expectMessage("Employee is not archived.");

        employee.reinstate(LocalDate.of(2011, 6, 15));
    }

    @Test
    public void removeSuccess() throws Exception {
        Employee employee = EmployeeBuilder.instance().archived().build();

        employee.remove();

        eventTracking.expectedLastEvent(EmployeeRemoved.class);
    }

    @Test
    public void removeNotArchived() throws Exception {
        Employee employee = EmployeeBuilder.instance().build();

        expectedEx.expectMessage("Cannot remove active employee.");

        employee.remove();
    }

    @Test
    public void rename() throws Exception {
        Employee employee = EmployeeBuilder.instance().build();

        employee.rename(name);

        assertEquals(name, employee.getName());
        eventTracking.expectedLastEvent(EmployeeRenamed.class);
    }
}