package kz.tanat.domain.employee;

import kz.tanat.domain.DomainEvent;
import kz.tanat.domain.DomainEventPublisher;
import kz.tanat.domain.DomainEventSubscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * Вспомогательный класс для работы с событиями системы.
 * Помогает фиксировать и тестировать события, произошедшие в системе.
 *
 * @author Tanat
 * @version 1.0
 * @since 07.07.2017.
 */
public class EventTracking {
    private List<Class<? extends DomainEvent>> handledEvents = new ArrayList<>();

    public EventTracking() {
        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<DomainEvent>() {
            @Override
            public void handleEvent(DomainEvent aDomainEvent) {
                handledEvents.add(aDomainEvent.getClass());
            }

            @Override
            public Class<DomainEvent> subscribedToEventType() {
                return DomainEvent.class;
            }
        });
    }

    public void expectedEvents(int anEventCount) {
        if (this.handledEvents.size() != anEventCount) {
            throw new IllegalStateException("Expected " + anEventCount + " events, but handled " + this.handledEvents.size()
                    + " events: " + this.handledEvents);
        }
    }

    public void expectedLastEvent(Class<? extends DomainEvent> aDomainEventType) {
        Class<? extends DomainEvent> lastEvent = this.handledEvents.get(this.handledEvents.size() - 1);
        if (lastEvent != aDomainEventType) {
            throw new IllegalStateException("Expected last " + aDomainEventType.getSimpleName() + " event, but handled event: " + lastEvent.getSimpleName());
        }
    }

    public void expectedEvent(Class<? extends DomainEvent> aDomainEventType) {
        this.expectedEvent(aDomainEventType, 1);
    }

    public void expectedEvent(Class<? extends DomainEvent> aDomainEventType, int aTotal) {
        int count = 0;

        for (Class<? extends DomainEvent> type : this.handledEvents) {
            if (type == aDomainEventType) {
                ++count;
            }
        }

        if (count != aTotal) {
            throw new IllegalStateException("Expected " + aTotal + " " + aDomainEventType.getSimpleName() + " events, but handled "
                    + this.handledEvents.size() + " events: " + this.handledEvents);
        }
    }
}
