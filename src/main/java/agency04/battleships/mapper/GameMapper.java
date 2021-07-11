package agency04.battleships.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import agency04.battleships.domain.Game;
import agency04.battleships.dto.GameDTO;
import agency04.battleships.dto.GameListDTO;
import agency04.battleships.dto.Games;

@Component
public class GameMapper {
	
	public GameDTO toDTO(Game game) {
		GameDTO gameDTO = new GameDTO();
		
		gameDTO.setPlayerId(game.getPlayer1().getIdPlayer());
		gameDTO.setOpponentId(game.getPlayer2().getIdPlayer());
		gameDTO.setGameId(game.getIdGame());
		gameDTO.setStarting(game.getStarting());
		
		return gameDTO;
	}
	
	public GameListDTO toDTO(List<Game> games, String idPlayer) {
		GameListDTO gamesDTO = new GameListDTO();
		List<Games> gameList = new ArrayList<>();
		
		for(Game game : games) {
			Games newGames = new Games();
			
			newGames.setIdGame(game.getIdGame());
			newGames.setStatus(game.getStatus());
			
			if (idPlayer.equals((game.getPlayer1().getIdPlayer()))) {
				newGames.setOpponent(game.getPlayer2().getIdPlayer());
			} else {
				newGames.setOpponent(game.getPlayer1().getIdPlayer());
			}
			
			gameList.add(newGames);
		}
		
		gamesDTO.setGames(gameList);
		
		return gamesDTO;
	}
}
