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
			List<String> board;
			
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
						board = game.getBoard2self();
						board.set(coordinate.getY(), board.get(coordinate.getY()).substring(0, coordinate.getX()) + "X" +  board.get(coordinate.getY()).substring(coordinate.getX() + 1));
						game.setBoard2self(board);
						board = game.getBoard1opponent();
						board.set(coordinate.getY(), board.get(coordinate.getY()).substring(0, coordinate.getX()) + "X" +  board.get(coordinate.getY()).substring(coordinate.getX() + 1));
						game.setBoard1opponent(board);
						hit = true;
						break;
					}
				}
				if (!hit) {
					salvoMap.put(shot, Shot.MISS);
				} else {
					if (salvoMap.get(shot).equals(Shot.KILL)) {
						List<Ship> ships = game.getShips2();
						ships.remove(hitShip);
						game.setShips2(ships);
					} else {
						List<Coordinate> coordinates = hitShip.getCoordinates();
						coordinates.remove(coordinate);
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
						board = game.getBoard1self();
						board.set(coordinate.getY(), board.get(coordinate.getY()).substring(0, coordinate.getX()) + "X" +  board.get(coordinate.getY()).substring(coordinate.getX() + 1));
						game.setBoard1self(board);
						board = game.getBoard2opponent();
						board.set(coordinate.getY(), board.get(coordinate.getY()).substring(0, coordinate.getX()) + "X" +  board.get(coordinate.getY()).substring(coordinate.getX() + 1));
						game.setBoard2opponent(board);
						hit = true;
						break;
					}
				}
				if (!hit) {
					salvoMap.put(shot, Shot.MISS);
				} else {
					if (salvoMap.get(shot).equals(Shot.KILL)) {
						List<Ship> ships = game.getShips1();
						ships.remove(hitShip);
						game.setShips1(ships);
					} else {
						List<Coordinate> coordinates = hitShip.getCoordinates();
						coordinates.remove(coordinate);
						hitShip.setCoordinates(coordinates);
					}
				}
			}
		}

		SalvoDTO salvoDTO = new SalvoDTO(salvoMap);

		gameRepository.save(game);
		
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
		
		return salvoDTO;
	}

}
