package kz.tanat.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Класс для работы с событиями системы (Шаблон Observer).
 *
 * @author Tanat
 * @since 07.07.2017.
 */
public class DomainEventPublisher {

    private static final ThreadLocal<DomainEventPublisher> instance = ThreadLocal.withInitial(() -> new DomainEventPublisher());

    private boolean publishing;

    @SuppressWarnings("rawtypes")
    private List subscribers;

    public static DomainEventPublisher instance() {
        return instance.get();
    }

    public <T> void publish(final T domainEvent) {
        if (!this.isPublishing() && this.hasSubscribers()) {

            try {
                this.setPublishing(true);

                Class<?> eventType = domainEvent.getClass();

                @SuppressWarnings("unchecked")
                List<DomainEventSubscriber<T>> allSubscribers = this.subscribers();

                for (DomainEventSubscriber<T> subscriber : allSubscribers) {
                    Class<?> subscribedToType = subscriber.subscribedToEventType();

                    if (eventType == subscribedToType || subscribedToType == DomainEvent.class) {
                        subscriber.handleEvent(domainEvent);
                    }
                }

            } finally {
                this.setPublishing(false);
            }
        }
    }

    public void publishAll(Collection<DomainEvent> domainEvents) {
        for (DomainEvent domainEvent : domainEvents) {
            this.publish(domainEvent);
        }
    }

    public void reset() {
        if (!this.isPublishing()) {
            this.setSubscribers(null);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> void subscribe(DomainEventSubscriber<T> subscriber) {
        if (!this.isPublishing()) {
            this.ensureSubscribersList();

            this.subscribers().add(subscriber);
        }
    }

    private DomainEventPublisher() {
        super();

        this.setPublishing(false);
        this.ensureSubscribersList();
    }

    @SuppressWarnings("rawtypes")
    private void ensureSubscribersList() {
        if (!this.hasSubscribers()) {
            this.setSubscribers(new ArrayList());
        }
    }

    private boolean isPublishing() {
        return this.publishing;
    }

    private void setPublishing(boolean flag) {
        this.publishing = flag;
    }

    private boolean hasSubscribers() {
        return this.subscribers() != null;
    }

    @SuppressWarnings("rawtypes")
    private List subscribers() {
        return this.subscribers;
    }

    @SuppressWarnings("rawtypes")
    private void setSubscribers(List subscriberList) {
        this.subscribers = subscriberList;
    }
}
