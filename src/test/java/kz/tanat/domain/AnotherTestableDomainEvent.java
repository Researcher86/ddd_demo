package kz.tanat.domain;

import java.time.LocalDate;

public class AnotherTestableDomainEvent implements DomainEvent {

    private int eventVersion;
    private double value;
    private LocalDate occurredOn;

    public AnotherTestableDomainEvent(double aValue) {
        super();

        this.eventVersion = 1;
        this.value = aValue;
        this.occurredOn = LocalDate.now();
    }

    public double getValue() {
        return value;
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
