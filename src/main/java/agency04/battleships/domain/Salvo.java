package agency04.battleships.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
* Salvo is list of fired shots in one turn.
* Each player is allowed to shoot a salvo of shots equalling the number of ships he
* controls that have not yet been destroyed.
* 
* @see Game
* @author Stjepan Ruklić
* 
*/

@Getter @Setter @NoArgsConstructor
public class Salvo {

	@JsonProperty("salvo")
	private List<String> salvo;
	
	
}
