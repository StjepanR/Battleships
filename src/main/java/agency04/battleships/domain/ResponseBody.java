package agency04.battleships.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
	
}
