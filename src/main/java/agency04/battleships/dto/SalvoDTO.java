package agency04.battleships.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import agency04.battleships.domain.enums.Shot;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class SalvoDTO {

	@JsonProperty("salvo")
	private Map<String, Shot> shots;

	public SalvoDTO(Map<String, Shot> shots) {
		this.shots = shots;
	}
	
}
