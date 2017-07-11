package kz.tanat.domain.employee;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Arrays;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.*;

/**
 * Тест хранилища данных об сотрудниках.
 *
 * @author Tanat
 * @version 1.1
 * @since 09.07.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class EmployeeRepositoryTest {
    @Autowired
    private EmployeeRepository repository;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void get() throws Exception {
        Employee employee = EmployeeBuilder.instance().build();
        repository.save(employee);

        Employee found = repository.getOne(employee.getId());

        assertNotNull(found);
        assertEquals(employee.getId(), found.getId());
    }

    @Test
    public void add() throws Exception {
        Employee employee = EmployeeBuilder.instance()
                .withPhones(new Phones(Arrays.asList(
                        new Phone(7, "888", "00000001"),
                        new Phone(7, "888", "00000002"))))
                .build();
        repository.save(employee);

        Employee found = repository.getOne(employee.getId());

        assertEquals(employee.getId(), found.getId());
        assertEquals(employee.getName(), found.getName());
        assertEquals(employee.getAddress(), found.getAddress());
        assertEquals(employee.getCreateDate(), found.getCreateDate());
        assertThat(employee.getPhones().getAll(), containsInAnyOrder(found.getPhones().getAll().toArray()));
        assertThat(employee.getStatus(), containsInAnyOrder(found.getStatus().toArray()));
    }

    @Test
    public void save() throws Exception {
        Employee employee = EmployeeBuilder.instance()
                .withPhones(new Phones(Arrays.asList(
                        new Phone(7, "888", "00000001"),
                        new Phone(7, "888", "00000002"))))
                .build();
        repository.save(employee);

        Employee edit = repository.getOne(employee.getId());

        edit.rename(new Name("New", "Test", "Name"));
        edit.addPhone(new Phone(7, "888", "00000003"));
        edit.archive(LocalDate.now());
        repository.save(edit);

        Employee found = repository.getOne(employee.getId());
        assertTrue(found.isArchived());
        assertEquals(edit.getName(), found.getName());
        assertThat(edit.getPhones().getAll(), containsInAnyOrder(found.getPhones().getAll().toArray()));
        assertThat(edit.getStatus(), containsInAnyOrder(found.getStatus().toArray()));
    }

    @Test
    public void remove() throws Exception {
        Employee employee = EmployeeBuilder.instance().build();
        this.repository.save(employee);

        Employee found = this.repository.findOne(employee.getId());
        assertNotNull(found);

        this.repository.delete(employee);

        found = this.repository.findOne(employee.getId());
        assertNull(found);
    }

    @Test
    public void nextId() throws Exception {
        for (int i = 0; i < 10; i++) {
            System.out.println(repository.nextId());
        }
    }

}