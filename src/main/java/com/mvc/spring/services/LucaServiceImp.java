package com.mvc.spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.spring.model.Game;
import com.mvc.spring.repository.GameRepository;

@Service
public class LucaServiceImp implements LucaService{
	
	@Autowired
	GameRepository repository;
	
	@Override
	public void save(Game game) {
		repository.save(game);
	}
	
	@Override
	public List<Game> getAllGames(){
		return repository.findAll();
	}
	
	@Override
	public Game findByName(String name){
		return repository.findByName(name);
	}
	
	@Override
	public void deleteGame(String name) {
		repository.delete(repository.findByName(name));
	}
	
	@Override
	public List<Game> gamesFilteredByGenre(String genre){
		return repository.findByGenre(genre);
	}

}
