package agency04.battleships.domain.enums;

import lombok.Getter;

@Getter
public enum Shot {
	HIT,
	KILL,
	MISS;
	
	private String shot;
	
}
