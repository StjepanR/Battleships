package agency04.battleships.service;


import agency04.battleships.domain.Player;
import agency04.battleships.domain.Players;


public interface PlayerService {
	
	Players listAll();
	
	Player createPlayer(Player player);
	
	Player findByIdPLayer(String id);

	Player findByEmail(String email);
	
	Player findByEmailNot(String email);
	
	int countByEmail(String email);
}
