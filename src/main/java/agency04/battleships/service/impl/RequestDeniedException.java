package agency04.battleships.service.impl;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequestDeniedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RequestDeniedException(String message) {
	    super(message);
	  }

}
