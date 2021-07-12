package agency04.battleships.rest;

import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import agency04.battleships.dao.PlayerRepository;
import agency04.battleships.domain.Game;
import agency04.battleships.domain.Player;
import agency04.battleships.domain.Salvo;
import agency04.battleships.domain.enums.Status;
import agency04.battleships.dto.GameListDTO;
import agency04.battleships.dto.GameTurn;
import agency04.battleships.dto.GameWin;
import agency04.battleships.dto.SalvoDTO;
import agency04.battleships.dto.SalvoFinishedDTO;
import agency04.battleships.dto.SalvoInProgressDTO;
import agency04.battleships.mapper.GameMapper;
import agency04.battleships.rest.exception.NoPlayerException;
import agency04.battleships.mapper.GameInProgressStatusMapper;
import agency04.battleships.service.GameService;
import agency04.battleships.service.PlayerService;
import agency04.battleships.service.SalvoService;

@RestController
@RequestMapping("/player")
public class PlayerController {

	@Autowired
	private PlayerRepository playerRepository;

	@Autowired
	private PlayerService playerService;

	@Autowired
	private GameService gameService;

	@Autowired
	private SalvoService salvoService;

	@Autowired
	private GameMapper gameMapper;

	@Autowired
	private GameInProgressStatusMapper gameStatusMapper;

	@PostMapping("")
	public ResponseEntity<?> addPlayer(@RequestBody Player player) {

		player = playerService.createPlayer(player);

		HttpHeaders responseHeader = new HttpHeaders();
		responseHeader.set("Location", "/player/" + player.getIdPlayer());

		return new ResponseEntity<>(responseHeader, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getMyProfile(@PathVariable("id") String idPlayer) {

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

		Game game = gameService.createGame(idPlayer1.get("player_id").toString(), idPlayer2);

		HttpHeaders responseHeader = new HttpHeaders();
		responseHeader.set("Location", "/game/" + game.getIdGame());

		return new ResponseEntity<>(gameMapper.toDTO(game), responseHeader, HttpStatus.CREATED);
	}

	/*
	 * Check if game belongs to player
	 * */
	@GetMapping("/{player_id}/game/{game_id}")
	public ResponseEntity<?> getGameStatus(@PathVariable("player_id") String idPlayer, @PathVariable("game_id") String idGame) {

		if (playerRepository.countByIdPlayer(idPlayer) == 0) {
			throw new NoPlayerException(idPlayer);
		}

		Game game = gameService.findByIdGame(idGame);

		return new ResponseEntity<>(gameStatusMapper.toDTO(game, idPlayer), HttpStatus.OK);
	}

	@GetMapping("/{player_id}/game/list")
	public ResponseEntity<?> getPlayerGames(@PathVariable("player_id") String idPlayer) {

		GameListDTO games = gameMapper.toDTO(gameService.findByPlayer1IdPlayerOrPlayer2IdPlayer(idPlayer, idPlayer), idPlayer);

		if (games.getGames().size() == 0) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(games, HttpStatus.OK);
	}

	@PutMapping("/{player_id}/game/{game_id}")
	public ResponseEntity<?> getGameStatus(@PathVariable("player_id") String idPlayer, @PathVariable("game_id") String idGame, @RequestBody Salvo salvo) {

		Game game = gameService.findByIdGame(idGame);
		Player player = playerService.findByIdPlayer(idPlayer);

		if (!game.getTurn().equals(idPlayer)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		
		if (idPlayer.equals(game.getPlayer1().getIdPlayer())) {
			if (salvo.getSalvo().size() > game.getShips1().size()) {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
		} else {
			if(salvo.getSalvo().size() > game.getShips2().size()) {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
		}
		

		if (game.getStatus().equals(Status.LOST) || game.getStatus().equals(Status.WON)) {
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
		}

		SalvoDTO salvoDTO = salvoService.fireSalvo(game, player, salvo);

		if (game.getStatus().equals(Status.IN_PROGRES)) {
			return new ResponseEntity<>(new SalvoInProgressDTO(salvoDTO, new GameTurn(game.getTurn())), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new SalvoFinishedDTO(salvoDTO, new GameWin(idPlayer)), HttpStatus.OK);
		}
	}
}
