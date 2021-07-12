package agency04.battleships.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import agency04.battleships.dao.GameRepository;
import agency04.battleships.domain.Coordinate;
import agency04.battleships.domain.Game;
import agency04.battleships.domain.Player;
import agency04.battleships.domain.Salvo;
import agency04.battleships.domain.Ship;
import agency04.battleships.domain.enums.Shot;
import agency04.battleships.domain.enums.Status;
import agency04.battleships.dto.SalvoDTO;

import agency04.battleships.service.SalvoService;

@Service
public class SalvoServiceImpl implements SalvoService {

	@Autowired
	private GameRepository gameRepository;
	
	@SuppressWarnings("unlikely-arg-type")
	@Override
	public SalvoDTO fireSalvo(Game game, Player player, Salvo salvo) {

		Map<String, Shot> salvoMap = new HashMap<>();

		for (String shot : salvo.getSalvo()) {
			Coordinate coordinate = Coordinate.getRealCoordinate(shot);
			
			Map<String, Integer> coordinateMap = new LinkedHashMap<>();
			coordinateMap.put("x", coordinate.getX());
			coordinateMap.put("y", coordinate.getY());
			
			Ship hitShip = null;
			boolean hit = false;
			
			if (player.getIdPlayer().equals(game.getPlayer1().getIdPlayer())) { //player 1 is shooting
				for (Ship ship : game.getShips2()) {
					if (ship.getCoordinates().contains(coordinateMap)) { // hit
						if (ship.getCoordinates().size() == 1) { // kill
							 hitShip = ship;
							salvoMap.put(shot, Shot.KILL);
						} else {
							hitShip = ship;
							salvoMap.put(shot, Shot.HIT);
						}
						game.setBoard2self(adjustBoard(game.getBoard2self(), coordinate, "X"));
						game.setBoard1opponent(adjustBoard(game.getBoard1opponent(), coordinate, "X"));
						hit = true;
						break;
					}
				}
				if (!hit) {
					salvoMap.put(shot, Shot.MISS);
					game.setBoard2self(adjustBoard(game.getBoard2self(), coordinate, "O"));
					game.setBoard1opponent(adjustBoard(game.getBoard1opponent(), coordinate, "O"));
				} else {
					if (salvoMap.get(shot).equals(Shot.KILL)) {
						List<Ship> ships = game.getShips2();
						ships.remove(hitShip);
						game.setShips2(ships);
					} else {
						List<Coordinate> coordinates = hitShip.getCoordinates();
						coordinates.remove(coordinateMap);
						hitShip.setCoordinates(coordinates);
					}
				}
			} else { // player 2 is shooting
				for (Ship ship : game.getShips1()) {
					if (ship.getCoordinates().contains(coordinateMap)) { // hit
						if (ship.getCoordinates().size() == 1) { // kill
							hitShip = ship;
							salvoMap.put(shot, Shot.KILL);
						} else {
							hitShip = ship;
							salvoMap.put(shot, Shot.HIT);
						}
						game.setBoard1self(adjustBoard(game.getBoard1self(), coordinate, "X"));
						game.setBoard2opponent(adjustBoard(game.getBoard2opponent(), coordinate, "X"));
						hit = true;
						break;
					}
				}
				if (!hit) {
					salvoMap.put(shot, Shot.MISS);
					game.setBoard1self(adjustBoard(game.getBoard1self(), coordinate, "O"));
					game.setBoard2opponent(adjustBoard(game.getBoard2opponent(), coordinate, "O"));
				} else {
					if (salvoMap.get(shot).equals(Shot.KILL)) {
						List<Ship> ships = game.getShips1();
						ships.remove(hitShip);
						game.setShips1(ships);
					} else {
						List<Coordinate> coordinates = hitShip.getCoordinates();
						coordinates.remove(coordinateMap);
						hitShip.setCoordinates(coordinates);
					}
				}
			}
		}

		SalvoDTO salvoDTO = new SalvoDTO(salvoMap);
		
		// UPGRADE: Listeners for status turn and board
		if (game.getPlayer1().getIdPlayer().equals(player.getIdPlayer())) {
			game.setTurn(game.getPlayer2().getIdPlayer());
		} else {
			game.setTurn(game.getPlayer1().getIdPlayer());
		}
		
		if (game.getShips1().size() == 0) {
			game.setStatus(Status.LOST);
		} else if (game.getShips2().size() == 0) {
			game.setStatus(Status.WON);
		}
		
		gameRepository.save(game);
		
		return salvoDTO;
	}
	
	public static List<String> adjustBoard(List<String> board, Coordinate coordinate, String str) {
		board.set(coordinate.getY(), board.get(coordinate.getY()).substring(0, coordinate.getX()) + str +  board.get(coordinate.getY()).substring(coordinate.getX() + 1));

		return board;
	}
}
