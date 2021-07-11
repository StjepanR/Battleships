package agency04.battleships.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class SalvoFinishedDTO {

	@JsonProperty("salvo")
	private SalvoDTO salvo;
	
	@JsonProperty("won")
	private GameWin won;

	public SalvoFinishedDTO(SalvoDTO salvo, GameWin won) {
		this.salvo = salvo;
		this.won = won;
	}
}
