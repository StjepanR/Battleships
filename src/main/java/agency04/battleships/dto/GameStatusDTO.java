package agency04.battleships.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import agency04.battleships.domain.GameTurn;
import agency04.battleships.domain.PlayerBoardStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class GameStatusDTO {

	@JsonProperty("self")
	private PlayerBoardStatus self;
	
	@JsonProperty("opponent")
	private PlayerBoardStatus opponent;
	
	@JsonProperty("game")
	private GameTurn game;
	
}
