package kz.tanat.app;

import kz.tanat.domain.DomainEvent;
import kz.tanat.domain.DomainEventPublisher;
import kz.tanat.domain.event.EventRepository;
import kz.tanat.domain.event.StoredEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Класс перехватывает вызовы к сервисам приложения и
 * регистрирует слушателя для перехвата всех событий системы.
 *
 * @author Tanat
 * @since 12.08.2017.
 */
@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class DomainEventMonitor {
    private final EventRepository eventRepository;

    @Around("execution(* kz.tanat.app..*Service.*(..))")
    public Object webServiceMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        DomainEventPublisher.instance().reset();

        DomainEventPublisher.instance().subscribe((DomainEvent domainEvent) -> {
            StoredEvent storedEvent = new StoredEvent(domainEvent);

            log.info("Event '{}': {}", domainEvent.getClass().getSimpleName(), storedEvent.eventBody());

            eventRepository.save(storedEvent);
        });

        return joinPoint.proceed();
    }
}
