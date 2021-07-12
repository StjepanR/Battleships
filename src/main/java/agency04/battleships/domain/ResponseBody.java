package agency04.battleships.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ResponseBody is custom response with errorCode and errorArg.
 * errorCode represents type of error.
 * errorArg represents resource that caused error.
 * 
 * @author Stjepan Ruklić
 *
 */
@Getter @Setter @NoArgsConstructor
public class ResponseBody {

	private String errorCode;
	
	private String errorArg;

	@JsonProperty("error-code")
	public String getErrorCode() {
		return errorCode;
	}
	
	@JsonProperty("error-arg")
	public String getErrorArg() {
		return errorArg;
	}
	
	public ResponseBody(String errorCode, String errorArg) {
		this.errorCode = errorCode;
		this.errorArg = errorArg;
	}
}
