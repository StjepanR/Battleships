package agency04.battleships.service;


import agency04.battleships.domain.Player;
import agency04.battleships.dto.Players;

/**
 * Manages Players in the system.
 * 
 * @see Player
 * @author Stjepan Ruklić
 * 
 */

public interface PlayerService {
	
	Players listAll();
	
	Player createPlayer(Player player);
	
	Player findByIdPlayer(String id);

	Player findByEmail(String email);
	
	Player findByEmailNot(String email);
	
	int countByEmail(String email);
}
