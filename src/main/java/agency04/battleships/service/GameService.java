package agency04.battleships.service;

import java.util.List;

import agency04.battleships.domain.Game;

public interface GameService {

	List<Game> listAll();
	
	Game createGame(String idPlayer1, String idPlayer2);
	
	Game findByIdGame(String idGame);
	
	List<Game> findByPlayer1IdPlayerOrPlayer2IdPlayer(String idPlayer1, String idPlayer2);
}
