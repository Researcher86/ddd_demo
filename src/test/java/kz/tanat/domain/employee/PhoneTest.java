package kz.tanat.domain.employee;

import kz.tanat.domain.employee.event.EmployeePhoneAdded;
import kz.tanat.domain.employee.event.EmployeePhoneRemoved;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Тестируем логику работы телефонных номеров сотрудников.
 *
 * @author Tanat
 * @version 1.0
 * @since 07.07.2017.
 */
public class PhoneTest {
    private EventTracking eventTracking;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        eventTracking = new EventTracking();
    }

    @Test
    public void add() {
        Employee employee = EmployeeBuilder.instance().build();
        Phone phone = new Phone(7, "888", "00000001");
        employee.addPhone(phone);
        assertFalse(employee.getPhones().getAll().isEmpty());
        assertTrue(employee.containsPhone(phone));

        eventTracking.expectedLastEvent(EmployeePhoneAdded.class);
    }

    @Test
    public void addExists() {
        Phone phone = new Phone(7, "888", "00000001");
        Employee employee = EmployeeBuilder.instance()
                .withPhones(new Phones(Arrays.asList(phone)))
                .build();

        expectedEx.expectMessage("Phone already exists.");

        employee.addPhone(phone);
    }

    @Test
    public void remove() {
        Employee employee = EmployeeBuilder.instance()
                .withPhones(new Phones(Arrays.asList(
                        new Phone(7, "888", "00000001"),
                        new Phone(7, "888", "00000002"))))
                .build();
        assertEquals(2, employee.getPhones().getAll().size());

        employee.removePhone(1);

        assertEquals(1, employee.getPhones().getAll().size());
        eventTracking.expectedLastEvent(EmployeePhoneRemoved.class);
    }

    @Test
    public void removeNotExists() {
        Employee employee = EmployeeBuilder.instance().build();
        expectedEx.expectMessage("Phone not found.");
        employee.removePhone(42);
    }

    @Test
    public void removeLast() {
        Employee employee = EmployeeBuilder.instance()
                .withPhones(new Phones(Arrays.asList(new Phone(7, "888", "00000001"))))
                .build();
        expectedEx.expectMessage("Cannot remove the last phone.");
        employee.removePhone(0);
    }
}