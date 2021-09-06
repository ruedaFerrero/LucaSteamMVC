package com.mvc.spring.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	/**
	 * Busca un juego por su ID
	 * @param id ID
	 * @return Juego, si no existe devuelve null
	 */
	public Game findById(Long id){
		Optional<Game> val = repository.findById(id);
		Game out = null;
		if(val.isPresent())
			out = val.get();
		return out;
	}

	@Override
	/**
	 * Elimina un juego por su ID
	 * 
	 * @author Jose
	 * @version 1.0, Septiembre 2021
	 * @param id
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
		return repository.findAllByGenreContaining(genre);
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

	/**
	 * Devuelve todos los juegos guardados en una lista de paginables
	 * @param pageable
	 * @return
	 */
	@Override
	public Page<Game> getAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	/**
	 * Devuelve todos los juegos que contentan en el nombre el parametro dado
	 * @param name
	 * @return
	 */
	@Override
        public List<Game> gamesFilteredByName(String name){
		return repository.findAllByNameContaining(name);
	}

	/**
	 * Devuelve todos los juegos cuya plataforma venga dada en el parametro
	 * @param name
	 * @return
	 */
	@Override
	public List<Game> gamesFilteredByPlatform(String name){
		return repository.findAllByPlatformContaining(name);
	}

	/**
	 * Devuelve todos los juegos cuyos publishers contentan en el nombre el parametro dado
	 * @param name
	 * @return
	 */
	@Override
	public List<Game> gamesFilteredByPublisher(String name){
		return repository.findAllByPublisherContaining(name);
	}

	/**
	 * Devuelve todos los juegos publicados en un año
	 * @param year
	 * @return
	 */
	@Override
	public List<Game> gamesFilteredByYear(Integer year){
		return repository.findAllByYear(year);
	}

	/**
	 * Devuelve todos los juegos cuyas ventas sean las dadas por parametro
	 * @param sales
	 * @return
	 */
	@Override
	public List<Game> gamesFilteredByEusales(Double sales){
		return repository.findAllByEuSales(sales);
	}

	/**
	 * Devuelve la lista de todas las editoras
	 * @return
	 */
	public List<String> getAllPublishers() {
		return repository.findAllPublishers();
	}   
   /**
	 * Devuelve la lista de todos juegos lanzados en año par
	 * @return List<Game> Lista de juegos
	 */
	public List<Game> getAllYearPairGames() {
		return repository.findAllYearPairGames();
	}
        
        /**
	 * Devuelve la lista de todos los juegos con ventas superiores a la media
	 * @return List<Game> Lista de juegos
	 */
	public List<Game> getAllSuperSalesGames() {
		return repository.findAllSuperSalesGames();
	}

	/**
	 * Devuelve una lista de todos los juegos para consola de nintendo
	 * @return
	 */
	public List<Game> getAllNintendoConsoleGames(){
		return repository.getAllNintendoConsoleGames("wii", "nes", "gb", "ds", "snes");
	}

	/**
	 * Devuelve una lista de juegos publicados en el siglo 20
	 * @return
	 */
	public Page<Game> getAllGamesReleasedIn20Century(Pageable page){
		return repository.findAllGamesFromXXCentury(page);
	}
}