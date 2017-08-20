package kz.tanat.domain.event;

import kz.tanat.domain.DomainEvent;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

/**
 * Сущность Хранить События.
 *
 * @author Tanat
 * @since 14.07.2017.
 */
@Entity
public class StoredEvent {
    @Id
    @GeneratedValue
    private Long eventId;
    private String typeName;
    private LocalDate occurredOn;
    @Column(length = 65000)
    private String eventBody;

    private StoredEvent() {
    }

    public StoredEvent(DomainEvent domainEvent) {
        this.typeName = domainEvent.getClass().getName();
        this.occurredOn = domainEvent.occurredOn();

        this.eventBody = EventSerializer.serialize(domainEvent);
    }

    public String eventBody() {
        return this.eventBody;
    }

    public Long eventId() {
        return this.eventId;
    }

    public LocalDate occurredOn() {
        return this.occurredOn;
    }

    @SuppressWarnings("unchecked")
    public <T extends DomainEvent> T toDomainEvent() {
        Class<T> domainEventClass = null;

        try {
            domainEventClass = (Class<T>) Class.forName(this.typeName());
        } catch (Exception e) {
            throw new IllegalStateException("Class load error, because: " + e.getMessage());
        }

        return EventSerializer.deserialize(eventBody, domainEventClass);
    }

    public String typeName() {
        return this.typeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StoredEvent that = (StoredEvent) o;

        return eventId != null ? eventId.equals(that.eventId) : that.eventId == null;
    }

    @Override
    public int hashCode() {
        return eventId != null ? eventId.hashCode() : 0;
    }

}
