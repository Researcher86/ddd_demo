package kz.tanat.domain;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Тестируем логику рассылки уведомлений системы.
 *
 * @author Tanat
 * @since 09.07.2017.
 */
public class DomainEventPublisherTest {

    private boolean anotherEventHandled;
    private boolean eventHandled;

    @Test
    public void testDomainEventPublisherPublish() throws Exception {
        DomainEventPublisher.instance().reset();

        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<TestableDomainEvent>() {
            @Override
            public void handleEvent(TestableDomainEvent domainEvent) {
                assertEquals(100L, domainEvent.getId());
                assertEquals("test", domainEvent.getName());
                eventHandled = true;
            }

            @Override
            public Class<TestableDomainEvent> subscribedToEventType() {
                return TestableDomainEvent.class;
            }
        });

        assertFalse(this.eventHandled);

        DomainEventPublisher.instance().publish(new TestableDomainEvent(100L, "test"));

        assertTrue(this.eventHandled);
    }

    @Test
    public void testDomainEventPublisherBlocked() throws Exception {
        DomainEventPublisher.instance().reset();

        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<TestableDomainEvent>() {
            @Override
            public void handleEvent(TestableDomainEvent domainEvent) {
                assertEquals(100L, domainEvent.getId());
                assertEquals("test", domainEvent.getName());
                eventHandled = true;
                // attempt nested publish, which should not work
                DomainEventPublisher.instance().publish(new AnotherTestableDomainEvent(1000.0));
            }

            @Override
            public Class<TestableDomainEvent> subscribedToEventType() {
                return TestableDomainEvent.class;
            }
        });

        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<AnotherTestableDomainEvent>() {
            @Override
            public void handleEvent(AnotherTestableDomainEvent domainEvent) {
                // should never be reached due to blocked publisher
                assertEquals(1000.0, domainEvent.getValue(), 0.001);
                anotherEventHandled = true;
            }

            @Override
            public Class<AnotherTestableDomainEvent> subscribedToEventType() {
                return AnotherTestableDomainEvent.class;
            }
        });

        assertFalse(this.eventHandled);
        assertFalse(this.anotherEventHandled);

        DomainEventPublisher.instance().publish(new TestableDomainEvent(100L, "test"));

        assertTrue(this.eventHandled);
        assertFalse(this.anotherEventHandled);
    }
}
