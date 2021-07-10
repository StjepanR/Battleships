package agency04.battleships.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import agency04.battleships.dao.PlayerRepository;
import agency04.battleships.domain.Player;
import agency04.battleships.domain.Players;
import agency04.battleships.service.PlayerService;


@Service
public class PlayerServiceImpl implements PlayerService {

    private static final String EMAIL_FORMAT = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private static final String ID_FORMAT = "^player-[1-9][0-9]*$";
    
	@Autowired
	private PlayerRepository playerRepository;
	
	@Override
	public Players listAll() {
		Players players = new Players();
		players.setPlayers(playerRepository.findAll());
		
		return players;
	}
	
	@Override
	public Player createPlayer(Player player) {
		Assert.notNull(player, "Player object must be given!");
		
		String name = player.getName();
		Assert.notNull(name, "Player name must be given!");
		
		String email = player.getEmail();
		Assert.notNull(email, "Player email must be given!");
        Assert.isTrue(email.matches(EMAIL_FORMAT), "Given email is in wrong format: '" + email + "'!");

        if(playerRepository.countByEmail(email) > 0 ) {
            throw new RequestDeniedException("Player with email " + email + "already exists!");
        }
				
		return playerRepository.save(player);
	}
	
	@Override
	public Player findByIdPLayer(String idPlayer) {
		Assert.notNull(idPlayer, "Player ID must be given!");
		Assert.isTrue(idPlayer.toString().matches(ID_FORMAT), "Player ID must be a digit greater than 0, not '" + idPlayer + "'!");

		return playerRepository.findByIdPLayer(idPlayer);
	}
	
	@Override
	public Player findByEmail(String email) {
		Assert.notNull(email, "Player email must be given!");
		
		return playerRepository.findByEmail(email);
	}

	@Override
	public Player findByEmailNot(String email) {
		Assert.notNull(email, "Player email must be given!");
		
		return playerRepository.findByEmailNot(email);
	}

	@Override
	public int countByEmail(String email) {
		Assert.notNull(email, "Player email must be given!");
		
		return playerRepository.countByEmail(email);
	}
	
}
