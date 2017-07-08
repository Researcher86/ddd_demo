package kz.tanat.domain;

import java.time.LocalDate;

/**
 * Интерфейс событий системы.
 *
 * @author Tanat
 * @version 1.0
 * @since 07.07.2017.
 */
public interface DomainEvent {

    int eventVersion();

    LocalDate occurredOn();
}
