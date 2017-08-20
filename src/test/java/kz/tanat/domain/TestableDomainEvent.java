package kz.tanat.domain;

import java.time.LocalDate;

/**
 * Тестовое событие.
 *
 * @author Tanat
 * @since 09.07.2017.
 */
public class TestableDomainEvent implements DomainEvent {

    private int eventVersion;
    private long id;
    private String name;
    private LocalDate occurredOn;

    public TestableDomainEvent(long anId, String aName) {
        super();

        this.eventVersion = 1;
        this.id = anId;
        this.name = aName;
        this.occurredOn = LocalDate.now();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public int eventVersion() {
        return eventVersion;
    }

    @Override
    public LocalDate occurredOn() {
        return this.occurredOn;
    }

}
