package agency04.battleships.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Coordinate {

	int x;
	
	int y;
	
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
