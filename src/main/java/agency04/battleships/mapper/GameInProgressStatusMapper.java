package agency04.battleships.mapper;

import org.springframework.stereotype.Component;


import agency04.battleships.domain.Game;
import agency04.battleships.dto.GameInProgressStatusDTO;
import agency04.battleships.dto.GameTurn;
import agency04.battleships.dto.PlayerBoardStatus;

@Component
public class GameInProgressStatusMapper {
	
	// response depends on who asked for status
	public GameInProgressStatusDTO toDTO(Game game, String idPlayer) {
		GameInProgressStatusDTO gameStautsDTO = new GameInProgressStatusDTO();
		
		
		PlayerBoardStatus self = new PlayerBoardStatus();
		PlayerBoardStatus opponent = new PlayerBoardStatus();

		if (idPlayer.equals(game.getPlayer1().getIdPlayer())) { // Player 1 is asking for status
			self.setPlayerId(idPlayer);
			self.setBoard(game.getBoard1self());
			gameStautsDTO.setSelf(self);
			
			opponent.setPlayerId(game.getPlayer2().getIdPlayer());
			opponent.setBoard(game.getBoard1opponent());
			gameStautsDTO.setOpponent(opponent);
		} else { // Player 2 is asking for status 
			self.setPlayerId(idPlayer);
			self.setBoard(game.getBoard2self());
			gameStautsDTO.setSelf(self);
			
			opponent.setPlayerId(game.getPlayer1().getIdPlayer());
			opponent.setBoard(game.getBoard2opponent());
			gameStautsDTO.setOpponent(opponent);
		}
		
		GameTurn turn = new GameTurn();
		turn.setTurn(game.getTurn());
		gameStautsDTO.setGame(turn);
		
		return gameStautsDTO;
	}
}
