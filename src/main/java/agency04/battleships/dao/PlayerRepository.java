package agency04.battleships.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import agency04.battleships.domain.Player;

public interface PlayerRepository extends JpaRepository<Player, Long>{
	
	Player findByIdPLayer(String idPlayer);
	
	Player findByEmail(String email);
	
	Player findByEmailNot(String email);
	
	int countByEmail(String email);
	 
}
