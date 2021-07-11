package agency04.battleships.mapper;

import agency04.battleships.domain.Game;
import agency04.battleships.dto.GameFinishedStatusDTO;
import agency04.battleships.dto.GameWin;
import agency04.battleships.dto.PlayerBoardStatus;

public class GameFinishedStatusMapper {

	public GameFinishedStatusDTO toDTOPlayer(Game game) {
		GameFinishedStatusDTO gameStautsDTO = new GameFinishedStatusDTO();
		
		PlayerBoardStatus self = new PlayerBoardStatus();
		self.setPlayerId(game.getPlayer1().getIdPlayer());
		self.setBoard(game.getBoard1self());
		gameStautsDTO.setSelf(self);
		
		PlayerBoardStatus opponent = new PlayerBoardStatus();
		opponent.setPlayerId(game.getPlayer2().getIdPlayer());
		opponent.setBoard(game.getBoard1opponent());
		gameStautsDTO.setOpponent(opponent);
		
		GameWin won = new GameWin();
		won.setWon(game.getTurn());
		gameStautsDTO.setWon(won);
		
		return gameStautsDTO;
	}
	
	public GameFinishedStatusDTO toDTOOpponent(Game game) {
		GameFinishedStatusDTO gameStautsDTO = new GameFinishedStatusDTO();
		
		PlayerBoardStatus self = new PlayerBoardStatus();
		self.setPlayerId(game.getPlayer1().getIdPlayer());
		self.setBoard(game.getBoard2self());
		gameStautsDTO.setSelf(self);
		
		PlayerBoardStatus opponent = new PlayerBoardStatus();
		opponent.setPlayerId(game.getPlayer2().getIdPlayer());
		opponent.setBoard(game.getBoard2opponent());
		gameStautsDTO.setOpponent(opponent);
		
		GameWin won = new GameWin();
		won.setWon(game.getTurn());
		gameStautsDTO.setWon(won);
		
		return gameStautsDTO;
	}
}
