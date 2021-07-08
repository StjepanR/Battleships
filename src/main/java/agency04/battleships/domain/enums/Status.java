package agency04.battleships.domain.enums;

public enum Status {
	LOST,
	WON,
	IN_PROGRES;

	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
