package agency04.battleships.domain;

import javax.persistence.Embeddable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter @Setter @NoArgsConstructor @EqualsAndHashCode
public class Coordinate {

	int x;
	
	int y;
	
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
