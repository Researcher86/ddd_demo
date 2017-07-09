package kz.tanat.domain.employee;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDate;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Базовый тест хранилища данных об сотрудниках.
 *
 * @author Tanat
 * @version 1.0
 * @since 09.07.2017.
 */
public abstract class AbstractEmployeeRepositoryTest {
    protected EmployeeRepository repository;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void get() throws Exception {
        Employee employee = EmployeeBuilder.instance().build();
        repository.add(employee);

        Employee found = repository.get(employee.getId());

        assertNotNull(found);
        assertEquals(employee.getId(), found.getId());
    }

    @Test
    public void getNotFound() throws Exception {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("Employee not found.");
        repository.get(new EmployeeId(25L));
    }

    @Test
    public void add() throws Exception {
        Employee employee = EmployeeBuilder.instance()
                .withPhones(new Phones(Arrays.asList(
                        new Phone(7, "888", "00000001"),
                        new Phone(7, "888", "00000002"))))
                .build();
        repository.add(employee);

        Employee found = repository.get(employee.getId());

        assertEquals(employee.getId(), found.getId());
        assertEquals(employee.getName(), found.getName());
        assertEquals(employee.getAddress(), found.getAddress());
        assertEquals(employee.getCreateDate(), found.getCreateDate());
        assertThat(found.getPhones().getAll(), is(employee.getPhones().getAll()));
        assertEquals(employee.getStatus(), found.getStatus());
    }

    @Test
    public void save() throws Exception {
        Employee employee = EmployeeBuilder.instance()
                .withPhones(new Phones(Arrays.asList(
                        new Phone(7, "888", "00000001"),
                        new Phone(7, "888", "00000002"))))
                .build();
        repository.add(employee);

        Employee edit = repository.get(employee.getId());

        edit.rename(new Name("New", "Test", "Name"));
        edit.addPhone(new Phone(7, "888", "00000003"));
        edit.archive(LocalDate.now());
        repository.save(edit);

        Employee found = repository.get(employee.getId());
        assertTrue(found.isArchived());
        assertEquals(edit.getName(), found.getName());
        assertThat(found.getPhones().getAll(), is(edit.getPhones().getAll()));
        assertEquals(edit.getStatus(), found.getStatus());
    }

    @Test
    public void remove() throws Exception {
        Employee employee = EmployeeBuilder.instance().withId(5L).build();
        this.repository.add(employee);
        this.repository.remove(employee);

        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("Employee not found.");

        this.repository.get(new EmployeeId(5L));
    }

    @Test
    public void nextId() throws Exception {
        for (int i = 0; i < 10; i++) {
            System.out.println(repository.nextId());
        }
    }

}