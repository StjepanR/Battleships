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
import agency04.battleships.mapper.GameMapper;
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
		
		try {
			playerService.createPlayer(player);
		} catch(RequestDeniedException exc) {
			ResponseBody responseBody = new ResponseBody();
			responseBody.setErrorArg("error.username-already-taken");
			responseBody.setErrorCode(player.getEmail());
			return new ResponseEntity<>(responseBody, HttpStatus.CONFLICT);
		}

		HttpHeaders responseHeader = new HttpHeaders();
	    responseHeader.set("Location", "/player/" + player.getIdPLayer());

		return new ResponseEntity<>(responseHeader, HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getMyProfile(@PathVariable("id") String id) {
		Player player;
	
		try {
			
			player = playerService.findByIdPLayer(id);
			
		} catch (IllegalArgumentException exc) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		if (player == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(player, HttpStatus.OK);
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
		
		if (playerRepository.countByIdPLayer(idPlayer1.get("player_id")) == 0) {
			ResponseBody responseBody = new ResponseBody();
			responseBody.setErrorCode("error.unknown-user-id");
			responseBody.setErrorArg(idPlayer1.get("player_id").toString());
			return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
		}
		
		if (playerRepository.countByIdPLayer(idPlayer2) == 0) {
			ResponseBody responseBody = new ResponseBody();
			responseBody.setErrorCode("error.unknown-user-id");
			responseBody.setErrorArg(idPlayer2);
			return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
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
			ResponseBody responseBody = new ResponseBody();
			responseBody.setErrorCode("error.unknown-game-id");
			responseBody.setErrorArg(idGame);
			return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
		}
		
		if (playerRepository.countByIdPLayer(idPlayer) == 0) {
			ResponseBody responseBody = new ResponseBody();
			responseBody.setErrorCode("error.unknown-user-id");
			responseBody.setErrorArg(idPlayer);
			return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
		}
		
		try {
			game = gameService.findByIdGame(idGame);
		} catch (IllegalArgumentException exc) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(gameStatusMapper.toDTO(game), HttpStatus.OK);
	}
}
