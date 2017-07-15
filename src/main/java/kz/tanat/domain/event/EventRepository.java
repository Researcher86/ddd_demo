package kz.tanat.domain.event;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Интерфейс хранилища системных событий.
 *
 * @author Tanat
 * @version 1.0
 * @since 14.07.2017.
 */
public interface EventRepository extends JpaRepository<StoredEvent, Long> {
}
