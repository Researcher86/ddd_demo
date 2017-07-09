package kz.tanat.domain.employee;

/**
 * Интерфейс хранилища данных по работе с агрегатом/сущностью сотрудник.
 *
 * @author Tanat
 * @version 1.0
 * @since 09.07.2017.
 */
public interface EmployeeRepository {

    Employee get(EmployeeId id);

    void add(Employee employee);

    void save(Employee employee);

    void remove(Employee employee);

    EmployeeId nextId();
}
