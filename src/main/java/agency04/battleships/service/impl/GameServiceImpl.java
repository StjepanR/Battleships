package agency04.battleships.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import agency04.battleships.dao.GameRepository;
import agency04.battleships.dao.PlayerRepository;
import agency04.battleships.domain.Game;
import agency04.battleships.domain.Player;
import agency04.battleships.rest.exception.NoGameException;
import agency04.battleships.rest.exception.NoPlayerException;
import agency04.battleships.service.GameService;
import agency04.battleships.service.PlayerService;

@Service
public class GameServiceImpl implements GameService {

	private static final String GAME_ID_FORMAT = "^game-[1-9][0-9]*$";
	
	private static final String PLAYER_ID_FORMAT = "^player-[1-9][0-9]*$";

	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private PlayerService playerService;
	
	@Override
	public List<Game> listAll() {
		return gameRepository.findAll();
	}

	@Override
	public Game createGame(String idPlayer1, String idPlayer2) {
		Assert.notNull(idPlayer1, "Player1 ID must be given!");
		Assert.isTrue(idPlayer2.toString().matches(PLAYER_ID_FORMAT), "Player ID must be a digit greater than 0, not '" + idPlayer1 + "'!");

		Assert.notNull(idPlayer2, "Player2 ID must be given!");
		Assert.isTrue(idPlayer2.toString().matches(PLAYER_ID_FORMAT), "Player ID must be a digit greater than 0, not '" + idPlayer2 + "'!");
		
		if (playerRepository.countByIdPlayer(idPlayer1) == 0) {
			throw new NoPlayerException(idPlayer1);
		}
		
		if (playerRepository.countByIdPlayer(idPlayer2) == 0) {
			throw new NoPlayerException(idPlayer2);
		}
		
		Player player1 = playerService.findByIdPlayer(idPlayer1); //or throw
		Player player2 = playerService.findByIdPlayer(idPlayer2);
		
		Game game = new Game(player1, player2);
		
		return gameRepository.save(game);
	}
	
	@Override
	public Game findByIdGame(String idGame) {
		Assert.notNull(idGame, "Game ID must be given!");
		Assert.isTrue(idGame.matches(GAME_ID_FORMAT), "Game ID in wrong format, '" + idGame + " is wrong'!");

		if (gameRepository.countByIdGame(idGame) == 0) {
			throw new NoGameException(idGame);
		}
		
		return gameRepository.findByIdGame(idGame);
		
	}

	@Override
	public List<Game> findByPlayer1IdPlayerOrPlayer2IdPlayer(String idPlayer1, String idPlayer2) {
		Assert.notNull(idPlayer1, "Player ID must be given!");
		Assert.isTrue(idPlayer1.toString().matches(PLAYER_ID_FORMAT), "Player ID must be a digit greater than 0, not '" + idPlayer1 + "'!");

		Assert.notNull(idPlayer2, "Player ID must be given!");
		Assert.isTrue(idPlayer2.toString().matches(PLAYER_ID_FORMAT), "Player ID must be a digit greater than 0, not '" + idPlayer2 + "'!");

		Assert.isTrue(idPlayer1.equals(idPlayer2), "Something went wrong!");

		if (playerRepository.countByIdPlayer(idPlayer1) == 0) {
			throw new NoPlayerException(idPlayer1);
		}
		
		return gameRepository.findByPlayer1IdPlayerOrPlayer2IdPlayer(idPlayer1, idPlayer2);
	}

}
