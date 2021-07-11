package agency04.battleships.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
public class GameController {

	@GetMapping("/{id}")
	public ResponseEntity<?> getGame(@PathVariable("id") String id) {
	
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
