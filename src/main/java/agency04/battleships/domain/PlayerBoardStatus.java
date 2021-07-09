package agency04.battleships.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class PlayerBoardStatus {

	@JsonProperty("player_id")
	private String playerId;
	
	@JsonProperty("board")
	private String[] board;
}
