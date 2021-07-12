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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * An enrolled user.
 * User is uniquely identified by internal system ID (a String)
 * or with the email (a string of maximum 255 characters).
 * Name and email are required.
 * 
 * @see Game
 * @author Stjepan Ruklić
 * 
 */

@Entity
@Getter @Setter @NoArgsConstructor
public class Player {

	@Id
	@JsonIgnore
	@NotNull
	@GeneratedValue(generator = "prod-generator")
	@GenericGenerator(name = "prod-generator", parameters = @Parameter(name = "prefix", value = "player"), strategy = "agency04.battleships.domain.generator.MyKeyGenerator")
	private String idPlayer;
	
	@NotNull
	private String name;
	
	@NotNull
	private String email;
	
	@OneToMany(mappedBy = "player1")
	@JsonIgnore
	private Set<Game> games1;
	
	@OneToMany(mappedBy = "player2")
	@JsonIgnore
	private Set<Game> games2;

}
