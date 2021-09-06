package com.mvc.spring.services;

import java.io.File;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mvc.spring.model.Game;

public interface LucaService {
	public void loadDataFromFile(File file);

	public void save(Game game);
	
	public List<Game> getAllGames();
	
	public Game findByName(String name);

	public Game findById(Long id);

	public void deleteGame(Long id);
	
	public List<Game> gamesFilteredByGenre(String genre);
	
	Page<Game> getAll(Pageable pageable);
        
	public List<Game> gamesFilteredByName(String name);
        
	public List<Game> gamesFilteredByPlatform(String platform);
        
	public List<Game> gamesFilteredByPublisher(String publisher);
        
	public List<Game> gamesFilteredByYear(Integer year);
        
	public List<Game> gamesFilteredByEusales(Double sales);

	public List<String> getAllPublishers();

  public List<Game> getAllYearPairGames();
        
  public List<Game> getAllSuperSalesGames();

  public List<Game> getAllNintendoConsoleGames();

  public List<Game> getAllGamesReleasedIn20Century();
}
