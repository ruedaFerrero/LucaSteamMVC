package com.mvc.spring.services;

import java.io.File;
import java.util.List;

import com.mvc.spring.model.Game;

public interface LucaService {
	public void loadDataFromFile(File file);

	public void save(Game game);
	
	public List<Game> getAllGames();
	
	public Game findByName(String name);
	
	public void deleteGame(String name);
	
	public List<Game> gamesFilteredByGenre(String genre);

}
