package agency04.battleships.domain;


import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter @Setter
public class Game {

	@Id
	@GeneratedValue(generator = "prod-generator")
	@GenericGenerator(name = "prod-generator", parameters = @Parameter(name = "prefix", value = "game"), strategy = "agency04.battleships.domain.generator.MyKeyGenerator")
	private String idGame;
	
	@ManyToOne
    @JoinColumn(name = "idPlayer1", nullable = false)
	private Player player1;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPlayer2", nullable = false)
	private Player player2;
	
	@NotNull
	private String starting;
	
	@NotNull
	private String[] board1;
	
	@NotNull
	private String[] board2;
	
	@NotNull
	private String turn;
	
	public Game() {
		this.board1 = new String[10];
		Arrays.fill(this.board1, "..........");
		
		this.board2 = new String[10];
		Arrays.fill(this.board2, "..........");
	}
}
