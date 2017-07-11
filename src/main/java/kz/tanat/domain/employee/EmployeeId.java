package kz.tanat.domain.employee;

import kz.tanat.domain.AbstractId;

import javax.persistence.Embeddable;
import java.util.UUID;

/**
 * Объект значение, идентификатор сотрудника.
 *
 * @author Tanat
 * @version 1.1
 * @since 07.07.2017.
 */
@Embeddable
public class EmployeeId extends AbstractId {
    private EmployeeId() {
    }

    public EmployeeId(UUID id) {
        super(id);
    }
}
