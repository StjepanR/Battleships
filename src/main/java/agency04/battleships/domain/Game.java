package agency04.battleships.domain;


import java.util.ArrayList;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonProperty;

import agency04.battleships.domain.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Game between two players. Consists of two players, starting, 
 * turn, status of the game, board of player1 ships, board of player2 ships,
 * player1s view of player2s board, player2s view of player1s board,
 * ship placements of player1, ship placements of player2, 
 * Uniquely identified by internal system ID (a String).
 * player1 is player who started the game (self) and the player2 is the 
 * one who is being challenged (opponent).
 * player1 and player2 ships are randomly placed onto board using java.util.Random
 * 
 * @see Player
 * @see Ship
 * @author Stjepan Ruklić
 * 
 */

@Entity
@Getter @Setter @NoArgsConstructor
public class Game {

	@Id
	@GeneratedValue(generator = "prod-generator")
	@GenericGenerator(name = "prod-generator", parameters = @Parameter(name = "prefix", value = "game"), strategy = "agency04.battleships.domain.generator.MyKeyGenerator")
	private String idGame;

	@ManyToOne
	@JoinColumn(name = "player1", nullable = false)
	private Player player1;

	@ManyToOne
	@JoinColumn(name = "player2", nullable = false)
	private Player player2;

	@NotNull
	@JsonProperty("starting")
	private String starting;

	@NotNull
	@JsonProperty("turn")
	private String turn;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@JsonProperty("status")
	private Status status;
	
	@NotNull
	@ElementCollection
	@JsonProperty("board_1_self")
	private List<String> board1self;

	@NotNull
	@ElementCollection
	@JsonProperty("board_2_self")
	private List<String> board2self;

	@NotNull
	@ElementCollection
	@JsonProperty("board_1_opponent")
	private List<String> board1opponent;

	@NotNull
	@ElementCollection
	@JsonProperty("board_2_opponent")
	private List<String> board2opponent;

	@NotNull
	@ElementCollection
	@JsonProperty("ships1")
	private List<Ship> ships1;
	
	@NotNull
	@ElementCollection
	@JsonProperty("ships2")
	private List<Ship> ships2;
	
	public Game(Player player1, Player player2) {
		this.ships1 = placeShipsRandomly();
		this.ships2 = placeShipsRandomly();
		this.board1self = placeShipsOnBoard(this.ships1);
		this.board2self = placeShipsOnBoard(this.ships2);
		this.board1opponent = new ArrayList<>(Collections.nCopies(10, ".........."));
		this.board2opponent = new ArrayList<>(Collections.nCopies(10, ".........."));
		this.player1 = player1;
		this.player2 = player2;
		this.status = Status.IN_PROGRES;
		this.starting = player1.getIdPlayer();
		
		Random random = new Random();
		if (random.nextInt(2) == 0) {
			this.turn = player1.getIdPlayer();
		} else {
			this.turn = player2.getIdPlayer();
		}
		
	}

	private List<Ship> placeShipsRandomly() {

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
			List<Coordinate> shipCoordinates = findFreeCoordonate(coordinates, ship.getSize());
			coordinates.removeAll(shipCoordinates);
			ship.setCoordinates(shipCoordinates);
		}

		return ships;
	}

	public List<Coordinate> findFreeCoordonate(Set<Coordinate> coordinates, int size) {
		Random random = new Random();
		List<Coordinate> shipCoordinates = new ArrayList<>();
		Coordinate coordinate = new Coordinate();

		boolean foundFit = true;
		
		while (true) {
			coordinate.setX(random.nextInt(10));
			coordinate.setY(random.nextInt(10));			

			if (coordinates.contains(coordinate)) { // starting coordinate is free
				if (size <= coordinate.getX() + 1) { // check if we can fit boat left
					for (int i = 1; i <= size; i++) {
						Coordinate pom = new Coordinate(coordinate.getX() - i, coordinate.getY());
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
						Coordinate pom = new Coordinate(coordinate.getX(), coordinate.getY() - i);
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
						Coordinate pom = new Coordinate(coordinate.getX() + i, coordinate.getY());
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
						Coordinate pom = new Coordinate(coordinate.getX(), coordinate.getY() + i);
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
	
	public List<String> placeShipsOnBoard(List<Ship> ships) {
		List<String> board = new ArrayList<>(Collections.nCopies(10, ".........."));
		
		for (Ship ship : ships) {
			for (Coordinate coordinate : ship.getCoordinates()) {
				board.set(coordinate.getY(), board.get(coordinate.getY()).substring(0, coordinate.getX()) + "#" + board.get(coordinate.getY()).substring(coordinate.getX() + 1));
			}
		}
		return board;
	}
}
