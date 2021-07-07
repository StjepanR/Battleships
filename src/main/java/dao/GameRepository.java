package dao;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Game;

public interface GameRepository extends JpaRepository<Game, Long> {

	Game findByIdGame(Long idGame);
}
