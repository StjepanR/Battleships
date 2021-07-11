package agency04.battleships.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import agency04.battleships.domain.Game;
import agency04.battleships.dto.GameListDTO;

public interface GameRepository extends JpaRepository<Game, String> {

	Game findByIdGame(String idGame);
	
	int countByIdGame(String idGame);
	
	List<Game> findByPlayer1IdPlayerOrPlayer2IdPlayer(String idPlayer1, String idPlayer2);
}
