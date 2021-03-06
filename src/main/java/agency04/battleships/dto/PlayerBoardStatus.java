package agency04.battleships.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class PlayerBoardStatus {

	@JsonProperty("player_id")
	private String playerId;
	
	@JsonProperty("board")
	private List<String> board;
}
