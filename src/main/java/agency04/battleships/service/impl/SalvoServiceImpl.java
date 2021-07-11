package agency04.battleships.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import agency04.battleships.domain.Coordinate;
import agency04.battleships.domain.Game;
import agency04.battleships.domain.Player;
import agency04.battleships.domain.Salvo;
import agency04.battleships.domain.Ship;
import agency04.battleships.domain.enums.Shot;
import agency04.battleships.dto.SalvoDTO;

import agency04.battleships.service.SalvoService;

@Service
public class SalvoServiceImpl implements SalvoService {

	@Override
	public SalvoDTO fireSalvo(Game game, Player player, Salvo salvo) {

		Map<String, Shot> salvoMap = new HashMap<>();

		for (String shot : salvo.getSalvo()) {
			Coordinate coordinate = Coordinate.getRealCoordinate(shot);

			// adjust board
			// remove ship if it is a kill
			// remove coordinate if hit
			// need to iterate through ships of other player
			if (player.getIdPlayer().equals(game.getPlayer1().getIdPlayer())) { //player 1 is shooting
				for (Ship ship : game.getShips1()) {
					if (ship.getCoordinates().contains(coordinate)) { // hit
						if (ship.getCoordinates().size() == 1) { // kill
							salvoMap.put(shot, Shot.KILL);
						} else {
							salvoMap.put(shot, Shot.HIT);
						}
					} else {
						salvoMap.put(shot, Shot.MISS);
					}
				}
			} else { // player 2 is shooting 
				for (Ship ship : game.getShips1()) {
					if (ship.getCoordinates().contains(coordinate)) { // hit
						if (ship.getCoordinates().size() == 1) { // kill
							salvoMap.put(shot, Shot.KILL);
						} else {
							salvoMap.put(shot, Shot.HIT);
						}
					} else { // miss
						salvoMap.put(shot, Shot.MISS);
					}
				}
			}

			salvoMap.put(shot, null);
		}

		SalvoDTO salvoDTO = new SalvoDTO(salvoMap);

		return salvoDTO;
	}

}
