package agency04.battleships.mapper;

import org.springframework.stereotype.Component;


import agency04.battleships.domain.Game;
import agency04.battleships.dto.GameInProgressStatusDTO;
import agency04.battleships.dto.GameTurn;
import agency04.battleships.dto.PlayerBoardStatus;

@Component
public class GameInProgressStatusMapper {
	
	public GameInProgressStatusDTO toDTOSelf(Game game) {
		GameInProgressStatusDTO gameStautsDTO = new GameInProgressStatusDTO();
		
		PlayerBoardStatus self = new PlayerBoardStatus();
		self.setPlayerId(game.getPlayer1().getIdPLayer());
		self.setBoard(game.getBoard1self());
		gameStautsDTO.setSelf(self);
		
		PlayerBoardStatus opponent = new PlayerBoardStatus();
		opponent.setPlayerId(game.getPlayer2().getIdPLayer());
		opponent.setBoard(game.getBoard1opoonent());
		gameStautsDTO.setOpponent(opponent);
		
		GameTurn turn = new GameTurn();
		turn.setTurn(game.getTurn());
		gameStautsDTO.setGame(turn);
		
		return gameStautsDTO;
	}
	
	public GameInProgressStatusDTO toDTOOpponent(Game game) {
		GameInProgressStatusDTO gameStautsDTO = new GameInProgressStatusDTO();
		
		PlayerBoardStatus self = new PlayerBoardStatus();
		self.setPlayerId(game.getPlayer1().getIdPLayer());
		self.setBoard(game.getBoard2self());
		gameStautsDTO.setSelf(self);
		
		PlayerBoardStatus opponent = new PlayerBoardStatus();
		opponent.setPlayerId(game.getPlayer2().getIdPLayer());
		opponent.setBoard(game.getBoard2opoonent());
		gameStautsDTO.setOpponent(opponent);
		
		GameTurn turn = new GameTurn();
		turn.setTurn(game.getTurn());
		gameStautsDTO.setGame(turn);
		
		return gameStautsDTO;
	}
}
