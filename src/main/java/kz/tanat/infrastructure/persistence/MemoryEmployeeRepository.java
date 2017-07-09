package kz.tanat.infrastructure.persistence;

import kz.tanat.domain.employee.Employee;
import kz.tanat.domain.employee.EmployeeId;
import kz.tanat.domain.employee.EmployeeRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Реализация хранилища в оперативной памяти.
 *
 * @author Tanat
 * @version 1.0
 * @since 09.07.2017.
 */
public class MemoryEmployeeRepository implements EmployeeRepository {
    private AtomicLong identity = new AtomicLong(1);
    private Map<EmployeeId, Employee> employeeMap = new HashMap<>();

    @Override
    public Employee get(EmployeeId id) {
        if (!employeeMap.containsKey(id)) {
            throw new IllegalArgumentException("Employee not found.");
        }

        return employeeMap.get(id);
    }

    @Override
    public void add(Employee employee) {
        employeeMap.put(employee.getId(), employee);
    }

    @Override
    public void save(Employee employee) {
        employeeMap.put(employee.getId(), employee);
    }

    @Override
    public void remove(Employee employee) {
        employeeMap.remove(employee.getId());
    }

    @Override
    public EmployeeId nextId() {
        return new EmployeeId(identity.getAndAdd(1));
    }
}
