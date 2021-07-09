package agency04.battleships.mapper;

import org.springframework.stereotype.Component;

import agency04.battleships.domain.Game;
import agency04.battleships.domain.GameTurn;
import agency04.battleships.domain.PlayerBoardStatus;
import agency04.battleships.dto.GameStatusDTO;

@Component
public class GameStatusMapper {
	
	public GameStatusDTO toDTO(Game game) {
		GameStatusDTO gameStautsDTO = new GameStatusDTO();
		
		PlayerBoardStatus self = new PlayerBoardStatus();
		self.setPlayerId(game.getPlayer1().getIdPLayer());
		self.setBoard(game.getBoard1());
		gameStautsDTO.setSelf(self);
		
		PlayerBoardStatus opponent = new PlayerBoardStatus();
		self.setPlayerId(game.getPlayer2().getIdPLayer());
		self.setBoard(game.getBoard2());
		gameStautsDTO.setOpponent(opponent);
		
		GameTurn turn = new GameTurn();
		turn.setTurn(game.getTurn());
		gameStautsDTO.setGame(turn);
		
		return gameStautsDTO;
	}
}
