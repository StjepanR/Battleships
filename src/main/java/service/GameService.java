package service;

import java.util.List;

import domain.Game;
import domain.Player;

public interface GameService {

	List<Game> listAll();
	
	Game createGame(Game game);
	
	Game findByIdGame(Long idGame);
}
