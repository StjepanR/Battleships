package agency04.battleships.service;

import agency04.battleships.domain.Game;
import agency04.battleships.domain.Player;
import agency04.battleships.domain.Salvo;
import agency04.battleships.dto.SalvoDTO;

/**
 * Manages Salvo in the system.
 * 
 * @see Salvo
 * @author Stjepan Ruklić
 * 
 */

public interface SalvoService {

	public SalvoDTO fireSalvo(Game game, Player player, Salvo salvo);
	
}
