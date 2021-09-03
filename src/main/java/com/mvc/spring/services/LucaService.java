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

	public List<Game> findFirst10(Long index);
	
	public void deleteGame(Long id);
	
	public List<Game> gamesFilteredByGenre(String genre);
	
	Page<Game> getAll(Pageable pageable);

}
