package agency04.battleships.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonProperty;

import agency04.battleships.domain.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Games {

	@JsonProperty("game_id")
	private String idGame;
	
	@JsonProperty("opponent_id")
	private String opponent;
	
	@Enumerated(EnumType.STRING)
	@JsonProperty("status")
	private Status status;
	
}
