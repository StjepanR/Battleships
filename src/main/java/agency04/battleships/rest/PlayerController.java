package agency04.battleships.rest;

import java.util.HashMap;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

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
import agency04.battleships.domain.Game;
import agency04.battleships.domain.Player;
import agency04.battleships.domain.ResponseBody;
import agency04.battleships.dto.GameDTO;
import agency04.battleships.mapper.GameMapper;
import agency04.battleships.service.GameService;
import agency04.battleships.service.PlayerService;
import agency04.battleships.service.impl.RequestDeniedException;

@RestController
@RequestMapping("/player")
public class PlayerController {
	
	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private GameMapper gameMapper;

	@PostMapping("")
	public ResponseEntity<?> addPlayer(@RequestBody Player player) {
		
		Player newPlayer;
		
		try {
			
			newPlayer = playerService.createPlayer(player);

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
	public ResponseEntity<?> getOpponent(@RequestBody Map<String, String> idPlayer, @PathVariable("id") String id) {
		
		Game game;
		
		try {
			game = gameService.createGame(idPlayer.get("player_id"), id);
		} catch (IllegalArgumentException exc) {
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		
		if (game.getPlayer1() == null || game.getPlayer2() == null) {
			
			ResponseBody responseBody = new ResponseBody();
			
			if (game.getPlayer1() == null) {
				responseBody.setErrorArg(idPlayer.get("id_player"));
			}
			responseBody.setErrorArg(id);
			responseBody.setErrorCode("error.unknown-user-id");
			
			return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
		}
		
		HttpHeaders responseHeader = new HttpHeaders();
	    responseHeader.set("Location", "/game/" + game.getIdGame());

	    return new ResponseEntity<>(gameMapper.toDTO(game), responseHeader, HttpStatus.CREATED);
	}
	
	@GetMapping("/{player_id}/game/{game_id}")
	public Game getGameStatus(@PathVariable("player_id") String player_id, @PathVariable("game_id") String game_id) {
		Game game;
		
		return null;
	}
}
