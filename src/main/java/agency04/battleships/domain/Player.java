package agency04.battleships.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Player {

	/*
	 * idPLayer represents ID of player
	*/
	@Id
	@JsonIgnore
	@GeneratedValue(generator = "prod-generator")
	@GenericGenerator(name = "prod-generator", parameters = @Parameter(name = "prefix", value = "player"), strategy = "agency04.battleships.domain.generator.MyKeyGenerator")
	private String idPLayer;
	
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
	//private Map<String, Shot> myShots; 
	
	/*
	 * Shots enemy fired on my board
	*/
	//private List<String> enemyShots;
	
	/*
	 * Game history
	*/
	@OneToMany(mappedBy = "player1")
	@JsonIgnore
	private Set<Game> games1;
	
	/*
	 * Game history
	*/
	@OneToMany(mappedBy = "player2")
	@JsonIgnore
	private Set<Game> games2;
	
	public Player(String idPLayer, @NotNull String email, @NotNull String name, Set<Game> games1, Set<Game> games2) {
		super();
		this.idPLayer = idPLayer;
		this.email = email;
		this.name = name;
		this.games1 = games1;
		this.games2 = games2;
	}

	public Player() {}
	
	public String getIdPLayer() {
		return idPLayer;
	}

	public void setIdPLayer(String idPLayer) {
		this.idPLayer = idPLayer;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public Set<Game> getGames1() {
		return games1;
	}

	public Set<Game> getGames2() {
		return games2;
	}

}
