package domain;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import domain.enums.Shot;

@Entity
public class Player {

	/*
	 * idPLayer represents ID of player
	*/
	@Id
	private Long idPLayer;
	
	/*
	 * Email of player
	*/
	@NotNull
	private String email;
	
	/*
	 * Name of the player
	*/
	@NotNull
	private String name;
	
	/*
	 * Shots i fired on enemies board
	*/
	@NotNull
	private Map<String, Shot> myShots; 
	
	/*
	 * Shots enemy fired on my board
	*/
	@NotNull
	private List<String> enemyShots;
	
	/*
	 * Game history
	*/
	@OneToMany(mappedBy = "player")
	@JsonIgnore
	private Set<Game> games;

	public Player(String email, @NotNull String name) {
		this.email = email;
		this.name = name;
	}

	public Long getIdPLayer() {
		return idPLayer;
	}

	public void setIdPLayer(Long idPLayer) {
		this.idPLayer = idPLayer;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public Map<String, Shot> getMyShots() {
		return myShots;
	}

	public List<String> getEnemyShots() {
		return enemyShots;
	}

	public Set<Game> getGames() {
		return games;
	}
	
}
