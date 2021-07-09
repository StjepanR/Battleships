package agency04.battleships.mapper;

import org.springframework.stereotype.Component;

import agency04.battleships.domain.Game;
import agency04.battleships.dto.GameDTO;

@Component
public class GameMapper {
	
	public GameDTO toDTO(Game game) {
		GameDTO gameDTO = new GameDTO();
		
		gameDTO.setPlayerId(game.getPlayer1().getIdPLayer());
		gameDTO.setOpponentId(game.getPlayer2().getIdPLayer());
		gameDTO.setGameId(game.getIdGame());
		gameDTO.setStarting(game.getStarting());
		
		return gameDTO;
	}
}
