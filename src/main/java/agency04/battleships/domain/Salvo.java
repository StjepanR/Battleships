package agency04.battleships.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Salvo {

	@JsonProperty("salvo")
	private List<String> salvo;
	
	
}
