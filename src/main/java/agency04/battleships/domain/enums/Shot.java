package agency04.battleships.domain.enums;

import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public enum Shot {
	HIT,
	KILL,
	MISS;
	
	private String shot;
	
}
