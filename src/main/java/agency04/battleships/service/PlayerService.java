package agency04.battleships.service;


import java.util.List;

import agency04.battleships.domain.Player;
import agency04.battleships.dto.Players;
import agency04.battleships.rest.exception.NoPlayerException;

/**
 * Manages Players in the system.
 * 
 * @see Player
 * @author Stjepan Ruklić
 * 
 */

public interface PlayerService {

	/**
	 * Lists all players in the system.
	 * @return a list with all players
	 */
	Players listAll();

	/**
	 * Creates a new player with given object Player which consists of name and email.
	 * @param player Player object, without ID set
	 * @return created Player object, with name set, email set and idPlayer set
	 * @throws IllegalArgumentException if email is in wrong format or name or email is null
	 * @throws ExistingPlayerException if another player with same email already exists in the database
	 */
	Player createPlayer(Player player);

	/**
	 * Finds the player with given ID.
	 * @param idPlayer given player ID
	 * @return player associated with given ID in the system
	 * @throws IllegalArgumentException if idPlayer is in wrong format or is null
	 * @throws NoPlayerException if there is no player associated with given ID
	 */
	Player findByIdPlayer(String idPlayer);

	/**
	 * Finds the player with given email.
	 * @param email players email
	 * @return player associated with given email in the system
	 * @throws IllegalArgumentException if email is in wrong format or is null
	 * @throws NoPlayerException if there is no player associated with given email
	 */
	Player findByEmail(String email);

	/**
	 * Lists all players in the system whose email is different from one we passed to function.
	 * @param email players email
	 * @return a list of player whose email is different from given one
	 * @throws IllegalArgumentException if email is in wrong format or is null
	 */
	List<Player> findByEmailNot(String email);

	/**
	 * Number of players with given email
	 * @param email players email
	 * @return a number of players in the system whose email is equal to the given one
	 * @throws IllegalArgumentException if email is in wrong format or is null
	 */
	int countByEmail(String email);
}
