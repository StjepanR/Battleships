package agency04.battleships.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import agency04.battleships.domain.Game;


public interface GameRepository extends JpaRepository<Game, Long> {

	Game findByIdGame(Long idGame);
}
