package service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import dao.GameRepository;
import domain.Game;
import domain.Player;
import service.GameService;
import service.PlayerService;

public class GameServiceImpl implements GameService {

	private static final String ID_FORMAT = "^[1-9][0-9]*$";
	
	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private PlayerService playerService;
	
	@Override
	public List<Game> listAll() {
		return gameRepository.findAll();
	}

	@Override
	public Game createGame(Game game) {
		Assert.notNull(game, "Game must be given!");
		
		Player player1 = game.getPlayer1();
		Player player2 = game.getPlayer2();

		Assert.notNull(player1, "Games player1 object must be given!");
		Assert.notNull(player2, "Games player2 object must be given!");

		
		game.setPlayer1(playerService.findByEmail(player1.getEmail()));

		game.setPlayer2(playerService.findByEmail(player2.getEmail()));

		return gameRepository.save(game);
	}
	
	@Override
	public Game findByIdGame(Long idGame) {
		Assert.notNull(idGame, "Game ID must be given!");
		Assert.isTrue(idGame.toString().matches(ID_FORMAT), "Game ID must be a digit greater than 0, not '" + idGame + "'!");

		return gameRepository.findByIdGame(idGame);
		
	}
}
