package kz.tanat.domain.employee;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.*;

/**
 * Тест хранилища данных об сотрудниках.
 *
 * @author Tanat
 * @since 09.07.2017.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
public class EmployeeRepositoryTest {
    @Autowired
    private EmployeeRepository repository;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    @Sql("/sql/insert-employees.sql")
    public void get() throws Exception {
        EmployeeId employeeId = new EmployeeId(UUID.fromString("5c95ef64-1e86-4f3c-b8fe-9b045b480169"));

        Employee found = repository.getOne(employeeId);

        assertNotNull(found);
        assertEquals(employeeId, found.getId());
    }

    @Test
//    @DataSet("empty.xml")
//    @ExpectedDataSet("/expected-employees.xml")
//    @Commit
    public void add() throws Exception {
        Employee employee = EmployeeBuilder.instance()
                .withPhones(Arrays.asList(
                        new Phone(7, "888", "00000001"),
                        new Phone(7, "888", "00000002")))
                .build();
        repository.save(employee);

        Employee found = repository.getOne(employee.getId());

        assertEquals(employee.getId(), found.getId());
        assertEquals(employee.getName(), found.getName());
        assertEquals(employee.getAddress(), found.getAddress());
        assertEquals(employee.getCreateDate(), found.getCreateDate());
        assertThat(employee.getPhones(), containsInAnyOrder(found.getPhones().toArray()));
        assertThat(employee.getStatus(), containsInAnyOrder(found.getStatus().toArray()));
    }

    @Test
    public void edit() throws Exception {
        Employee employee = EmployeeBuilder.instance()
                .withPhones(Arrays.asList(
                        new Phone(7, "888", "00000001"),
                        new Phone(7, "888", "00000002")))
                .build();
        repository.save(employee);

        Employee stored = repository.getOne(employee.getId());

        stored.rename(new Name("New", "Test", "Name"));
        stored.addPhone(new Phone(7, "888", "00000003"));
        stored.archive(LocalDate.now());
        repository.save(stored);

        Employee found = repository.getOne(employee.getId());
        assertTrue(found.isArchived());
        assertEquals(stored.getName(), found.getName());
        assertThat(stored.getPhones(), containsInAnyOrder(found.getPhones().toArray()));
        assertThat(stored.getStatus(), containsInAnyOrder(found.getStatus().toArray()));
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