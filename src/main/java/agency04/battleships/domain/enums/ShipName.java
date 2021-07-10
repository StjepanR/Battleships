package agency04.battleships.domain.enums;

import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public enum ShipName {
	BATTLESHIP,
	DESTROYER,
	SUBMARINE,
	PATRL_CRAFT;
	
	private String name;
	
}
