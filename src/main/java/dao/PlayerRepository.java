package dao;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Player;

public interface PlayerRepository extends JpaRepository<Player, String>{
	
	Player findByIdPLayer(Long idPlayer);
	
	Player findByEmail(String email);
	
	Player findByEmailNot(String email);
	
	int countByEmail(String email);
	 
}
