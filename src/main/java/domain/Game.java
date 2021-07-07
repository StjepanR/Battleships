package domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import domain.enums.Status;

@Entity
public class Game {

	@Id
	private Long idGame;
	
	@NotNull
	private Player player1;
	
	@ManyToOne
    @JoinColumn(name = "email", nullable = false)
	private Player player2;
	
	@NotNull
	private Status status;

	public Long getIdGame() {
		return idGame;
	}

	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public Status getStatus() {
		return status;
	}

	public void setIdGame(Long idGame) {
		this.idGame = idGame;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
}
