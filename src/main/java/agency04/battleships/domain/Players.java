package agency04.battleships.domain;

import java.util.List;

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
