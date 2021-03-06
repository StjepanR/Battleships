package agency04.battleships.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class GameTurn {

	@JsonProperty("player_turn")
	private String turn;

	public GameTurn(String turn) {
		this.turn = turn;	
	}
}
