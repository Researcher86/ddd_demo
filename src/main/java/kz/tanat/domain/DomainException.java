package kz.tanat.domain;

/**
 * Исключительные ситуации системы.
 *
 * @author Tanat
 * @since 07.07.2017.
 */
public class DomainException extends RuntimeException {
	public DomainException(String message) {
		super(message);
	}
}
