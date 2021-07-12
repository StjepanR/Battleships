package agency04.battleships.service;

import java.util.List;

import agency04.battleships.domain.Game;
import agency04.battleships.rest.exception.NoGameException;
import agency04.battleships.rest.exception.NoPlayerException;


/**
 * Manages Games in the system.
 * 
 * @see Game
 * @author Stjepan Ruklić
 * 
 */

public interface GameService {

	/**
	 * Lists all games.
	 * @return iterable containing all games
	 */
	List<Game> listAll();

	/**
	 * Creates a new game with two given player IDs.
	 * @param idPlayer1 identifies starter of the game
	 * @param idPlayer2 identifies other player of the game
	 * @return created Game object, with player1 set, player2 set, list ships1 set, list ships2 set, 
	 * list board1self set, list board2selfset, list board1opponent set, list board2opponent set, 
	 * status set, turn set and starting set
	 * @throws IllegalArgumentException if one of given IDs is in wrong format or if it is null
	 * @throws NoPlayerException if one of the player with that ID is not found in the system 
	 */
	Game createGame(String idPlayer1, String idPlayer2);

	/**
	 * Finds game with given ID, if exists.
	 * @param idGame given game ID
	 * @return game associated with given ID
	 * @throws NoGameException if game with that ID is not found in the system
	 * @throws IllegalArgumentException if given ID is in wrong format or if it is null
	 */
	Game findByIdGame(String idGame);

	/**
	 * Lists all games in which player with ID idPlayer participated.
	 * @param idPLayer given player ID
	 * @return list contains all games player with ID idPlayer played
	 * @throws NoPlayerException if player with that ID is not found in the system
	 * @throws IllegalArgumentException if given ID is in wrong format or if it is null
	 */
	List<Game> findByPlayer1IdPlayerOrPlayer2IdPlayer(String idPlayer1, String idPlayer2);
}
