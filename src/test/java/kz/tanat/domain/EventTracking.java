package kz.tanat.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Вспомогательный класс для работы с событиями системы.
 * Помогает фиксировать и тестировать события, произошедшие в системе.
 *
 * @author Tanat
 * @since 07.07.2017.
 */
public class EventTracking {
	private List<Class<? extends DomainEvent>> handledEvents = new ArrayList<>();
	private List<DomainEvent> events = new ArrayList<>();

	public EventTracking() {
		DomainEventPublisher.instance().subscribe((DomainEvent domainEvent) -> {
			handledEvents.add(domainEvent.getClass());
			events.add(domainEvent);
		});
	}

	public void expectedEvents(int anEventCount) {
		if (this.handledEvents.size() != anEventCount) {
			throw new IllegalStateException("Expected " + anEventCount + " events, but handled " + this.handledEvents.size()
					+ " events: " + this.handledEvents);
		}
	}

	public void expectedLastEvent(Class<? extends DomainEvent> aDomainEventType) {
		if (this.handledEvents.get(this.handledEvents.size() - 1) != aDomainEventType) {
			throw new IllegalStateException("Expected last " + aDomainEventType.getSimpleName() + " event, but handled event: " + this.handledEvents.get(this.handledEvents.size() - 1).getSimpleName());
		}
	}

	public DomainEvent getLastEvent() {
		return this.events.get(this.events.size() - 1);
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
