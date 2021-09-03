package com.mvc.spring.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.mvc.spring.model.Game;
import com.mvc.spring.repository.GameRepository;

@Service

/**
 * Clase LucaServiceImp
 * 
 * Clase que implementa de la interfaz LucaService los métodos 
 * para trabajar con los datos de la aplicación
 * 
 */
public class LucaServiceImp implements LucaService {

	@Autowired
	private GameRepository repository;
	
	@Override
	/**
	 * Guarda un juego
	 * 
	 * @author Jose
	 * @version 1.0, Septiembre 2021
	 * @param game
	 */
	public void save(Game game) {
		repository.save(game);
	}

	@Override
	/**
	 * Devuelve la lista de juegos al completo
	 * 
	 * @author Jose
	 * @version 1.0, Septiembre 2021
	 * @return List<Game>
	 */
	public List<Game> getAllGames() {
		return repository.findAll();
	}

	@Override
	/**
	 * Devuelve un juego de la lista filtrado por nombre
	 * 
	 * @author Jose
	 * @version 1.0, Septiembre 2021
	 * @param name
	 * @return Game
	 */
	public Game findByName(String name) {
		return repository.findByName(name);
	}

	public Game findById(Long id){
		Optional<Game> val = repository.findById(id);
		Game out = null;
		if(val.isPresent())
			out = val.get();
		return out;
	}

	@Override
	public List<Game> findFirst10(Long index) {
		return repository.findFirst10ByIdGreaterThan(index);
	}

	@Override
	/**
	 * Elimina un juego por name
	 * 
	 * @author Jose
	 * @version 1.0, Septiembre 2021
	 * @param name
	 */
	public void deleteGame(Long id) {
		Game game = findById(id);
		if(game != null)
			repository.delete(game);
	}

	@Override
	/**
	 * Devuelve una List<Game> de juegos filtradas por genre
	 * 
	 * @author Jose
	 * @version 1.0, Septiembre 2021
	 * @param genre
	 * @return <List>Game
	 */
	public List<Game> gamesFilteredByGenre(String genre) {
		return repository.findByGenre(genre);
	}

	@Override
	/**
	 * Carga los datos de la BBDD desde un archivo "file"
	 * 
	 * @author Jose
	 * @version 1.0, Septiembre 2021
	 * @param file
	 */
	public void loadDataFromFile(File file) {
		if (repository.findAll().size() != 0) {
			return;
		}

		List<Game> games = new ArrayList<Game>();
		Scanner sc = null;
		try {
			sc = new Scanner(new File(file.getAbsolutePath()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		sc.useDelimiter(","); // sets the delimiter pattern
		sc.nextLine();
		while (sc.hasNext()) // returns a boolean value
		{
			sc.next();
			String name = sc.next();
			String platform = sc.next();
			Integer date;
			try {
				date = Integer.parseInt(sc.next());
			} catch (NumberFormatException e) {
				date = 0;
			}

			String genre = sc.next();
			String publisher = sc.next();
			sc.next();
			Double eu_sales;
			try {
				eu_sales = Double.parseDouble(sc.next());
			} catch (NumberFormatException e) {
				eu_sales = 0d;
			}
			games.add(new Game(name, platform, date, genre, publisher, eu_sales));
			sc.nextLine();
		}
		sc.close();

		repository.saveAll(games);
	}

	public Page<Game> testes(){
		Page<Game> page = repository.findAll(
				PageRequest.of(0, 1, Sort.by(Sort.Direction.ASC, "id")));

		return page;
	}
}
