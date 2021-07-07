package service;

import java.util.List;

import org.springframework.stereotype.Service;

import domain.Game;

@Service
public interface GameService {

	List<Game> listAll();
	
	Game createGame(Game game);
	
	Game findByIdGame(Long idGame);
}
