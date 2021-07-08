package agency04.battleships.domain;

import java.util.List;

public class Players {

	List<Player> players;

	public Players(List<Player> players) {
		this.players = players;
	}

	public Players() {}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	
}
