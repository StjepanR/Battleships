package agency04.battleships.mapper;

import agency04.battleships.domain.Game;
import agency04.battleships.dto.GameFinishedStatusDTO;
import agency04.battleships.dto.GameTurn;
import agency04.battleships.dto.GameWin;
import agency04.battleships.dto.PlayerBoardStatus;

public class GameFinishedStatusMapper {

	public GameFinishedStatusDTO toDTO(Game game) {
		GameFinishedStatusDTO gameStautsDTO = new GameFinishedStatusDTO();
		
		PlayerBoardStatus self = new PlayerBoardStatus();
		self.setPlayerId(game.getPlayer1().getIdPLayer());
		self.setBoard(game.getBoard1());
		gameStautsDTO.setSelf(self);
		
		PlayerBoardStatus opponent = new PlayerBoardStatus();
		self.setPlayerId(game.getPlayer2().getIdPLayer());
		self.setBoard(game.getBoard2());
		gameStautsDTO.setOpponent(opponent);
		
		GameWin won = new GameWin();
		won.setWon(game.getTurn());
		gameStautsDTO.setWon(won);
		
		return gameStautsDTO;
	}
}
