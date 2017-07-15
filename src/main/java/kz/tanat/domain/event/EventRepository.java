package kz.tanat.domain.event;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Интерфейс хранилища системных событий.
 *
 * @author Tanat
 * @since 14.07.2017.
 */
public interface EventRepository extends JpaRepository<StoredEvent, Long> {
}
