package com.mvc.spring.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

	@Override
	public void loadDataFromFile(File file) {
		if(repository.findAll().size() != 0){
			return;
		}

		List<Game> games = new ArrayList<Game>();
		Scanner sc = null;
		try {
			sc = new Scanner(new File(file.getAbsolutePath()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		sc.useDelimiter(",");   //sets the delimiter pattern
		sc.nextLine();
		while (sc.hasNext())  //returns a boolean value
		{
			sc.next();
			String name = sc.next();
			String platform = sc.next();
			Integer date;
			try {
				date = Integer.parseInt(sc.next());
			}   catch (NumberFormatException e){
				date = 0;
			}

			String genre = sc.next();
			String publisher = sc.next();
			sc.next();
			Double eu_sales;
			try{
				eu_sales  =Double.parseDouble(sc.next());
			} catch (NumberFormatException e){
				eu_sales = 0d;
			}
			games.add(new Game(name, platform, date, genre, publisher, eu_sales));
			sc.nextLine();
		}
		sc.close();

		repository.saveAll(games);
	}

}
