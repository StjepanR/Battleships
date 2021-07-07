package service;

import java.util.List;

import org.springframework.stereotype.Service;

import domain.Player;

@Service
public interface PlayerService {
	
	List<Player> listAll();
	
	Player createPlayer(Player player);
	
	Player findByIdPLayer(Long idPlayer);

	Player findByEmail(String email);
	
	Player findByEmailNot(String email);
	
	int countByEmail(String email);
}
