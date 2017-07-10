package kz.tanat.domain.employee;

import kz.tanat.domain.AbstractId;

import java.util.UUID;

/**
 * Объект значение, идентификатор сотрудника.
 *
 * @author Tanat
 * @version 1.0
 * @since 07.07.2017.
 */
public class EmployeeId extends AbstractId {
    public EmployeeId(UUID id) {
        super(id);
    }
}
