package agency04.battleships.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import agency04.battleships.domain.enums.ShipName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Ship {

	private int size;
	
	private ShipName name;
	
	private Set<Coordinate> coordinates;
	
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


