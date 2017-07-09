package kz.tanat.infrastructure.persistence;

import kz.tanat.domain.employee.AbstractEmployeeRepositoryTest;
import org.junit.Before;

/**
 * Тестируем реализацию хранилища в памяти.
 *
 * @author Tanat
 * @version 1.0
 * @since 09.07.2017.
 */
public class MemoryEmployeeRepositoryTest extends AbstractEmployeeRepositoryTest {

    @Before
    public void setUp() throws Exception {
        repository = new MemoryEmployeeRepository();
    }

}