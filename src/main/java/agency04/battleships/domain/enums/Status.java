package agency04.battleships.domain.enums;

import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public enum Status {
	LOST,
	WON,
	IN_PROGRES;

	private String status;

}
