package domain;

import com.fasterxml.jackson.annotation.JsonProperty;

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
	
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public void setErrorArg(String errorArg) {
		this.errorArg = errorArg;
	}
	
}
