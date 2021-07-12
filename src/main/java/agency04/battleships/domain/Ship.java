package agency04.battleships.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonProperty;

import agency04.battleships.domain.enums.ShipName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Each player owns (and must place) the following set of battleships in his grid, exact
 * number of respective ships is outlined below.
 * 1 - BATTLESHIP (takes 4 spaces on 10x10 grid)
 * 2 - DESTROYER (takes 3 spaces on 10x10 grid)
 * 3 - SUBMARINE (takes 2 spaces on 10x10 grid)
 * 4 - PATROLCRAFT (takes 1 spaces on 10x10 grid)
 * 
 * When a game is initiated, positions of the ships in the grid are fixed, and they do not
 * change throughout the game.
 * Ships cannot overlap on the grid.
 * A ship can be placed vertically or horizontally.
 * Ships are placed at random places when starting a game.
 * 
 * @see Game
 * @author Stjepan Ruklić
 * 
 */

@Embeddable
@Getter @Setter @NoArgsConstructor
public class Ship {

	@JsonProperty("size")
	private int size;
	
	@Enumerated(EnumType.STRING)
	@JsonProperty("name")
	private ShipName name;
	
	@JsonProperty("coordinates")
	private List<Coordinate> coordinates;
	
	public Ship(int size, ShipName name) {
		this.size = size;
		this.name = name;
	}
	
	public static ShipName getNameFromSize(int size) {
		switch(size) {
		case 1: 
			return ShipName.PATRL_CRAFT;
		case 2:
			return ShipName.SUBMARINE;
		case 3:
			return ShipName.DESTROYER;
		case 4:
			return ShipName.BATTLESHIP;
		default:
			throw new IllegalArgumentException();
		}
		
	}
	
	public static List<Ship> clone (int number, int size) {
		List<Ship> ships = new ArrayList<>();
		
		for (int i = 0; i < number; i++) {
			ships.add(new Ship(size, getNameFromSize(size)));
		}
		
		return ships;
	}
}


