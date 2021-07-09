package agency04.battleships.domain;

import java.util.Set;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter @Setter @NoArgsConstructor
public class Player {

	/*
	 * idPLayer represents ID of player
	*/
	@Id
	@JsonIgnore
	@NotNull
	@GeneratedValue(generator = "prod-generator")
	@GenericGenerator(name = "prod-generator", parameters = @Parameter(name = "prefix", value = "player"), strategy = "agency04.battleships.domain.generator.MyKeyGenerator")
	private String idPLayer;
	
	/*
	 * Name of the player
	*/
	@NotNull
	private String name;
	
	/*
	 * Email of player
	*/
	@NotNull
	private String email;
	
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
	@OneToMany(mappedBy = "player1", fetch = FetchType.LAZY)
	@JsonIgnore
	private Set<Game> games1;
	
	/*
	 * Game history
	*/
	@OneToMany(mappedBy = "player2", fetch = FetchType.LAZY)
	@JsonIgnore
	private Set<Game> games2;

}
