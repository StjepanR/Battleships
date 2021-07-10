package agency04.battleships.domain.enums;

import lombok.Getter;

@Getter
public enum Status {
	LOST,
	WON,
	IN_PROGRES;

	private String status;

}
