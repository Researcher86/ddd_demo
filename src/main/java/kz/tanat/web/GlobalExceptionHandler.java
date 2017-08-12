package kz.tanat.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Глобальный обработчик сообщений об ошибках.
 *
 * @author Tanat
 * @since 15.07.2017.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not found")
	public void exceptionHandler404(IllegalArgumentException e) {
		log.error("REST api exception", e);
	}
}
