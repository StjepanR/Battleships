package agency04.battleships.rest;

import java.util.HashMap;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import agency04.battleships.domain.Player;
import agency04.battleships.domain.ResponseBody;
import agency04.battleships.service.PlayerService;
import agency04.battleships.service.impl.RequestDeniedException;

@RestController
@RequestMapping("/player")
public class PlayerController {
	
	@Autowired
	private PlayerService playerService;

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
		
		Map<String, String> responseHeader = new HashMap<>();
		responseHeader.put("Location", "/player/" + newPlayer.getIdPLayer());
		
		return new ResponseEntity<>(responseHeader, HttpStatus.CREATED);
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getMyProfile(@PathVariable("id") String id) {
		Player player = playerService.findByIdPLayer(id);
		
		if (player == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(player, HttpStatus.OK);
	}
	
	@GetMapping("/list")
	public ResponseEntity<?> getPlayers() {
		return new ResponseEntity<>(playerService.listAll(), HttpStatus.OK);
	}
	
	@PostMapping("/{id}/game")
	public ResponseEntity<?> getOpponent(@PathVariable("id") String id) {
		
		if (playerService.findByIdPLayer(id) == null) {
			return new ResponseEntity<>(HttpStatus.CONFLICT); 
		}
		
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
