package kz.tanat.domain;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;
import java.time.LocalDate;

/**
 * Базовый класс для всех сущностей системы.
 *
 * @author Tanat
 * @since 11.07.2017.
 */
@MappedSuperclass
public abstract class AbstractEntity {
    @Version
    private Integer version;

    private LocalDate createdAt;
    private LocalDate updatedAt;

    @PrePersist
    public void beforePersist() {
        createdAt = LocalDate.now();
    }

    @PreUpdate
    public void beforeUpdate() {
        updatedAt = LocalDate.now();
    }
}
