package kz.tanat.domain;

/**
 * Интерфейс подписчиков на события системы.
 *
 * @author Tanat
 * @version 1.0
 * @since 07.07.2017.
 */
public interface DomainEventSubscriber<T> {

    void handleEvent(final T domainEvent);

    Class<T> subscribedToEventType();
}
