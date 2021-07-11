package agency04.battleships.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import agency04.battleships.dao.GameRepository;
import agency04.battleships.dao.PlayerRepository;
import agency04.battleships.domain.Game;
import agency04.battleships.domain.Player;
import agency04.battleships.domain.ResponseBody;
import agency04.battleships.dto.GameListDTO;
import agency04.battleships.mapper.GameMapper;
import agency04.battleships.restException.ExistingPlayerException;
import agency04.battleships.restException.NoGameException;
import agency04.battleships.restException.NoPlayerException;
import agency04.battleships.mapper.GameInProgressStatusMapper;
import agency04.battleships.service.GameService;
import agency04.battleships.service.PlayerService;
import agency04.battleships.service.impl.RequestDeniedException;

@RestController
@RequestMapping("/player")
public class PlayerController {
	
	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private GameMapper gameMapper;
	
	@Autowired
	private GameInProgressStatusMapper gameStatusMapper;

	@PostMapping("")
	public ResponseEntity<?> addPlayer(@RequestBody Player player) {

		if (playerRepository.countByEmail(player.getEmail()) > 0) {
			throw new ExistingPlayerException(player.getEmail());
		}
		
		player = playerService.createPlayer(player);

		HttpHeaders responseHeader = new HttpHeaders();
	    responseHeader.set("Location", "/player/" + player.getIdPlayer());

		return new ResponseEntity<>(responseHeader, HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getMyProfile(@PathVariable("id") String idPlayer) {
	
		if (playerRepository.countByIdPlayer(idPlayer) == 0) {
			throw new NoPlayerException(idPlayer);
		}
		
		return new ResponseEntity<>(playerRepository.findByIdPlayer(idPlayer), HttpStatus.OK);
	}
	
	@GetMapping("/list")
	public ResponseEntity<?> getPlayers() {
		return new ResponseEntity<>(playerService.listAll(), HttpStatus.OK);
	}
	
	/*
	 * PathVariable id represents opponents player ID
	 * RequestBody id represents starting player ID - me as a game player
	 * */
	@PostMapping("/{id}/game")
	public ResponseEntity<?> getOpponent(@RequestBody Map<String, String> idPlayer1, @PathVariable("id") String idPlayer2) {
		
		Game game;
		
		if (playerRepository.countByIdPlayer(idPlayer1.get("player_id")) == 0) {
			throw new NoPlayerException(idPlayer1.get("player_id"));
		}
		
		if (playerRepository.countByIdPlayer(idPlayer2) == 0) {
			throw new NoPlayerException(idPlayer2);
		}
		
		try {
			game = gameService.createGame(idPlayer1.get("player_id").toString(), idPlayer2);
		} catch (IllegalArgumentException exc) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		HttpHeaders responseHeader = new HttpHeaders();
	    responseHeader.set("Location", "/game/" + game.getIdGame());

	    return new ResponseEntity<>(gameMapper.toDTO(game), responseHeader, HttpStatus.CREATED);
	}
	
	@GetMapping("/{player_id}/game/{game_id}")
	public ResponseEntity<?> getGameStatus(@PathVariable("player_id") String idPlayer, @PathVariable("game_id") String idGame) {
		Game game;
		
		if (gameRepository.countByIdGame(idGame) == 0) {
			throw new NoGameException(idGame);
		}
		
		if (playerRepository.countByIdPlayer(idPlayer) == 0) {
			throw new NoPlayerException(idPlayer);
		}
		
		try {
			game = gameService.findByIdGame(idGame);
		} catch (IllegalArgumentException exc) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		if (idPlayer.equals(game.getStarting())) {
			return new ResponseEntity<>(gameStatusMapper.toDTOSelf(game), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(gameStatusMapper.toDTOOpponent(game), HttpStatus.OK);
		}	
	}
	
	@GetMapping("/{player_id}/game/list")
	public ResponseEntity<?> getPlayerGames(@PathVariable("player_id") String idPlayer) {
		
		if (playerRepository.countByIdPlayer(idPlayer) == 0) {
			throw new NoPlayerException(idPlayer);
		}
		
		GameListDTO games = gameMapper.toDTO(gameService.findByPlayer1IdPlayerOrPlayer2IdPlayer(idPlayer, idPlayer), idPlayer);
		
		if (games.getGames().size() == 0) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<>(games, HttpStatus.OK);
	}
}
