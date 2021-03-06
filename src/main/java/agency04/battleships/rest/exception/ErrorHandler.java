package agency04.battleships.rest.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import agency04.battleships.domain.ResponseBody;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(NoGameException.class)
	protected ResponseEntity<?> handleNoGameException(Exception exception, WebRequest webRequest) {

		return new ResponseEntity<>(new ResponseBody("error.unknown-game-id", exception.getMessage()), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NoPlayerException.class)
	protected ResponseEntity<?> handleNoPlayerException(Exception exception, WebRequest webRequest) {

		return new ResponseEntity<>(new ResponseBody("error.unknown-user-id", exception.getMessage()), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ExistingPlayerException.class)
	protected ResponseEntity<?> handleExistingPlayerException(Exception exception, WebRequest webRequest) {

		return new ResponseEntity<>(new ResponseBody("error.username-already-taken", exception.getMessage()), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	protected ResponseEntity<?> handleIllegalArgumentException(Exception exception, WebRequest webRequest) {

		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		final Map<String, String> errors = ex.getBindingResult().getAllErrors().stream()
				.collect(Collectors.toMap(
						error -> ((FieldError) error).getField(),
						error -> Optional.ofNullable(error.getDefaultMessage()).orElse(""))
						);
		
		return handleExceptionInternal(ex, errors, headers, status, request);
	}

}