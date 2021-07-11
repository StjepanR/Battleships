package agency04.battleships.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class SalvoInProgressDTO {

	@JsonProperty("salvo")
	private SalvoDTO salvo;
	
	@JsonProperty("game")
	private GameTurn game;
	
	public SalvoInProgressDTO(SalvoDTO salvo, GameTurn game) {
		this.salvo = salvo;
		this.game = game;
	}
}
