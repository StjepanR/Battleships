package agency04.battleships.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import agency04.battleships.domain.Player;

public interface PlayerRepository extends JpaRepository<Player, String>{
	
	Player findByIdPlayer(String idPlayer);
	
	Player findByEmail(String email);
	
	List<Player> findByEmailNot(String email);
	
	int countByIdPlayer(String idPlayer);
	
	int countByEmail(String email);
	
}
