package agency04.battleships.domain;


import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonIgnore;

import agency04.battleships.domain.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter @Setter @NoArgsConstructor
public class Game {

	@Id
	@JsonIgnore
	@GeneratedValue(generator = "prod-generator")
	@GenericGenerator(name = "prod-generator", parameters = @Parameter(name = "prefix", value = "game"), strategy = "agency04.battleships.domain.generator.MyKeyGenerator")
	private String idGame;
	
	@ManyToOne
    @JoinColumn(name = "idPlayer", nullable = false, insertable = false, updatable = false)
	private Player player1;
	
	@ManyToOne
    @JoinColumn(name = "idPlayer", nullable = false, insertable = false, updatable = false)
	private Player player2;
	
	@NotNull
	private Status status;
}
