package agency04.battleships.dto;

import java.util.List;

import agency04.battleships.domain.Player;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Players {

	List<Player> players;

	public Players(List<Player> players) {
		this.players = players;
	}

}
