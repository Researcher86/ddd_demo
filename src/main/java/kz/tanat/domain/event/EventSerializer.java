package kz.tanat.domain.event;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kz.tanat.domain.DomainEvent;

import java.io.IOException;

public final class EventSerializer {
	private static final ObjectMapper MAPPER = new ObjectMapper();

	static {
		MAPPER.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
		MAPPER.setVisibility(PropertyAccessor.CREATOR, JsonAutoDetect.Visibility.ANY);
		MAPPER.findAndRegisterModules();
	}

	public static String serialize(DomainEvent domainEvent) {
		try {
			return MAPPER.writeValueAsString(domainEvent);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public static <T extends DomainEvent> T deserialize(String serialization, final Class<T> type) {
		try {
			return MAPPER.readValue(serialization, type);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
}
