package kz.tanat.domain;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

		DomainEventPublisher.instance().subscribe((TestableDomainEvent domainEvent) -> {
			Assert.assertEquals(100L, domainEvent.getId());
			Assert.assertEquals("test", domainEvent.getName());
			eventHandled = true;
		});

		assertFalse(this.eventHandled);

		DomainEventPublisher.instance().publish(new TestableDomainEvent(100L, "test"));

		assertTrue(this.eventHandled);
	}

	@Test
	public void testDomainEventPublisherBlocked() throws Exception {
		DomainEventPublisher.instance().reset();

		DomainEventPublisher.instance().subscribe((TestableDomainEvent domainEvent) -> {
			Assert.assertEquals(100L, domainEvent.getId());
			Assert.assertEquals("test", domainEvent.getName());
			eventHandled = true;
			// attempt nested publish, which should not work
			DomainEventPublisher.instance().publish(new AnotherTestableDomainEvent(1000.0));
		});

		DomainEventPublisher.instance().subscribe((AnotherTestableDomainEvent domainEvent) -> {
			// should never be reached due to blocked publisher
			Assert.assertEquals(1000.0, domainEvent.getValue(), 0.001);
			anotherEventHandled = true;
		});

		assertFalse(this.eventHandled);
		assertFalse(this.anotherEventHandled);

		DomainEventPublisher.instance().publish(new TestableDomainEvent(100L, "test"));

		assertTrue(this.eventHandled);
		assertFalse(this.anotherEventHandled);
	}
}
