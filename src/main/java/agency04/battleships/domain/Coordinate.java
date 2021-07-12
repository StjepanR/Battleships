package agency04.battleships.domain;

import java.util.Map;

import javax.persistence.Embeddable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Coordinate represents position on board.
 * The playing board consists of 10x10 grid, where the rows are numbered using the
 * decimal number system (1 – 10) and the columns are marked alphabetically (A – J).
 * 
 * @see Ship
 * @author Stjepan Ruklić
 * 
 */

@Embeddable
@Getter @Setter @NoArgsConstructor @EqualsAndHashCode
public class Coordinate {

	public final static Map<String, Integer> coordinatesMapping = Map.of("A", 0, "B", 1, "C", 2, "D", 3, "E", 4, "F", 5, "G", 6, "H", 7, "I", 8, "J", 9);
	
	int x;
	
	int y;
	
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public static Coordinate getRealCoordinate(String stringCoordinate) {
		String[] splitted = stringCoordinate.split("x");
		
		return new Coordinate(Integer.parseInt(splitted[0]) - 1, coordinatesMapping.get(splitted[1]));
	}
}
