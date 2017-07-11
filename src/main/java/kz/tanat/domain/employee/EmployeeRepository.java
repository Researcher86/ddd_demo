package kz.tanat.domain.employee;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Интерфейс хранилища данных по работе с агрегатом/сущностью сотрудник.
 *
 * @author Tanat
 * @version 1.2
 * @since 09.07.2017.
 */
public interface EmployeeRepository extends JpaRepository<Employee, EmployeeId> {

    default EmployeeId nextId() {
        return new EmployeeId(UUID.randomUUID());
    }
}
