package agency04.battleships.domain;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter @Setter
public class Game {

	@Id
	@GeneratedValue(generator = "prod-generator")
	@GenericGenerator(name = "prod-generator", parameters = @Parameter(name = "prefix", value = "game"), strategy = "agency04.battleships.domain.generator.MyKeyGenerator")
	private String idGame;

	@ManyToOne
	@JoinColumn(name = "idPlayer1", nullable = false)
	private Player player1;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPlayer2", nullable = false)
	private Player player2;

	@NotNull
	private String starting;

	@NotNull
	private String[] board1;

	@NotNull
	private String[] board2;

	@NotNull
	private String turn;
	
	@NotNull
	private List<Ship> ships1;
	
	@NotNull
	private List<Ship> ships2;
	
	public Game() {
		this.board1 = new String[10];
		Arrays.fill(this.board1, "..........");

		this.board2 = new String[10];
		Arrays.fill(this.board2, "..........");
		
		this.ships1 = placeShips();
		this.ships2 = placeShips();
		this.starting = player1.getIdPLayer();
		
		Random random = new Random();
		if (random.nextInt(2) == 0) {
			this.turn = player1.getIdPLayer();
		}
		this.turn = player2.getIdPLayer();
	
	}

	private List<Ship> placeShips() {

		List<Ship> ships = new ArrayList<>();

		int size = 4;
		for (int number = 1; number <= 4; number++) {
			ships.addAll(Ship.clone(number, size--));
		}

		Set<Coordinate> coordinates = new HashSet<>();
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				Coordinate coordinate = new Coordinate(i, j);
				coordinates.add(coordinate);
			}
		}

		for (Ship ship : ships) {
			ship.setCoordinates(findFreeCoordonate(coordinates, ship.getSize()));
		}

		return ships;
	}

	public Set<Coordinate> findFreeCoordonate(Set<Coordinate> coordinates, int size) {
		Random random = new Random();
		Set<Coordinate> shipCoordinates = new HashSet<>();
		Coordinate coordinate = new Coordinate();

		boolean foundFit = true;
		
		Coordinate pom = new Coordinate();
		
		while (true) {
			coordinate.setX(random.nextInt(10));
			coordinate.setY(random.nextInt(10));			

			if (coordinates.contains(coordinate)) { // starting coordinate is free
				if (size <= coordinate.getX() + 1) { // check if we can fit boat left
					for (int i = 1; i <= size; i++) {
						pom.setX(coordinate.getX() - i);
						pom.setY(coordinate.getY());
						shipCoordinates.add(pom);
						if (!coordinates.contains(pom)) { // coordinate is used
							foundFit = false;
							shipCoordinates.clear();
							break;
						}
					}
					if (foundFit) {
						break;
					}
					foundFit = true;
				} else if (size <= coordinate.getY() + 1) { // check if we can fit boat up
					for (int i = 1; i <= size; i++) {
						pom.setX(coordinate.getX());
						pom.setY(coordinate.getY() - i);
						shipCoordinates.add(pom);
						if (!coordinates.contains(pom)) { // coordinate is used
							foundFit = false;
							shipCoordinates.clear();
							break;
						}
					}
					if (foundFit) {
						break;
					}
					foundFit = true;
				} else if (coordinate.getX() + size - 1 <= 9) { // check if we can fit boat right
					for (int i = 1; i <= size; i++) {
						pom.setX(coordinate.getX() + i);
						pom.setY(coordinate.getY());
						shipCoordinates.add(pom);
						if (!coordinates.contains(pom)) { // coordinate is used
							foundFit = false;
							shipCoordinates.clear();
							break;
						}
					}
					if (foundFit) {
						break;
					}
					foundFit = true;
				} else if (coordinate.getY() + size - 1 <= 9) { // check if we can fit boat down
					for (int i = 1; i <= size; i++) {
						pom.setX(coordinate.getX());
						pom.setY(coordinate.getY() + i);
						shipCoordinates.add(pom);
						if (!coordinates.contains(pom)) { // coordinate is used
							foundFit = false;
							shipCoordinates.clear();
							break;
						}
					}
					if (foundFit) {
						break;
					}
					foundFit = true;
				}
			}	
		}
		return shipCoordinates;
	}
}
