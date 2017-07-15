package kz.tanat.domain.event;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kz.tanat.domain.DomainEvent;

import javax.persistence.*;
import java.io.IOException;
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

    @Transient
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        MAPPER.setVisibility(PropertyAccessor.CREATOR, JsonAutoDetect.Visibility.ANY);
        MAPPER.findAndRegisterModules();
    }

    private StoredEvent() {
    }

    public StoredEvent(DomainEvent domainEvent) {
        this.typeName = domainEvent.getClass().getName();
        this.occurredOn = domainEvent.occurredOn();

        try {
            this.eventBody = MAPPER.writeValueAsString(domainEvent);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
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

        try {
            return MAPPER.readValue(this.eventBody, domainEventClass);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
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
