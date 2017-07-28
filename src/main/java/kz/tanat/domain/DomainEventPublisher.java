package kz.tanat.domain;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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
    private List<Subscriber> subscribers;

    private static class Subscriber {
        private DomainEventSubscriber subscriber;
        private Class<DomainEvent> eventType;

        public Subscriber(DomainEventSubscriber subscriber, Class<DomainEvent> eventType) {
            this.subscriber = subscriber;
            this.eventType = eventType;
        }
    }

    public static DomainEventPublisher instance() {
        return instance.get();
    }

    public <T> void publish(final T domainEvent) {
        if (!this.isPublishing() && this.hasSubscribers()) {

            try {
                this.setPublishing(true);

                Class<?> eventType = domainEvent.getClass();

                @SuppressWarnings("unchecked")
                List<Subscriber> allSubscribers = this.subscribers();

                for (Subscriber subscriber : allSubscribers) {
                    Class<?> subscribedToType = subscriber.eventType;

                    if (eventType == subscribedToType || subscribedToType == DomainEvent.class) {
                        subscriber.subscriber.handleEvent(domainEvent);
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

//            Class<T> aClass = subscriber.getClass().;
//            System.out.println(aClass.getSimpleName());

//            try {
//                Type type = subscriber.getClass().getGenericInterfaces()[0];
//                Type generic = ((ParameterizedType) type).getActualTypeArguments()[0];
//                Class<DomainEvent> clazz = (Class<DomainEvent>) Class.forName(generic.getTypeName());
//                this.subscribers().add(new Subscriber(subscriber, clazz));
//            } catch (ClassNotFoundException e) {
//                throw new RuntimeException(e);
//            }


            Type[] genericInterfaces = subscriber.getClass().getGenericInterfaces();
            for (Type genericInterface : genericInterfaces) {
                if (genericInterface instanceof ParameterizedType) {
                    Type[] genericTypes = ((ParameterizedType) genericInterface).getActualTypeArguments();
                    for (Type genericType : genericTypes) {
                        System.out.println("Generic type: " + genericType);
                    }
                }
            }

            Class<T> thisClass = null;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Type[] typeArguments = parameterizedType.getActualTypeArguments();
                thisClass = (Class<T>) typeArguments[0];
                System.out.println("Generic type: " + thisClass);
            }
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
            this.setSubscribers(new ArrayList<>());
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
    private List<Subscriber> subscribers() {
        return this.subscribers;
    }

    @SuppressWarnings("rawtypes")
    private void setSubscribers(List<Subscriber> subscriberList) {
        this.subscribers = subscriberList;
    }
}
