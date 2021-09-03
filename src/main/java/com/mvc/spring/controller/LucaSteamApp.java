package com.mvc.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mvc.spring.model.Game;
import com.mvc.spring.services.LucaService;

import java.io.File;

@Controller
public class LucaSteamApp {
	
	@Autowired
	LucaService service;
	
	/**
	 * Método que lleva a la página con el formulario para añadir un juego.
	 * 
	 * @author Jose
	 * @version 1.0, Septiembre 2021
	 * @param m Model
	 * @param game objeto tipo Game vacío
	 * @return la página addGameForm
	 */
	@GetMapping("/add")
	public String newGame(Model m, Game game) {
		m.addAttribute("game", game);
		return "addGameForm";
	}
	
	/**
	 * Método que actualiza la tabla de datos guardando un juego en ella, ya sea añadiéndolo nuevo o actualizando uno ya existente.
	 * 
	 * @author Jose
	 * @version 1.0, Septiembre 2021
	 * @param game objeto tipo Game a guardar
	 * @return redirecciona a la página inicial, actualizándola
	 */
	@PostMapping("/save")
	public String saveGame(Game game) {
		service.save(game);
		return("redirect:/");
	}
	
	/**
	 * Método que dirige al formulario para editar la información de un juego tomando el nombre del juego a modificar.
	 * 
	 * @author Jose
	 * @version 1.0, Septiembre 2021
	 * @param name nombre del juego a editar
	 * @param m Model
	 * @return la página addGameForm
	 */
	@GetMapping("/edit")
	public String editGame(@RequestParam("name") String name, Model m) {
		m.addAttribute("game", service.findByName(name));
		return "addGameForm";
	}
	
	/**
	 * Método que llama a la función deleteGame(String) de la capa servicios.
	 * 
	 * @author Jose
	 * @version 1.0, Septiembre 2021
	 * @param name nombre del juego a eliminar
	 * @return redirecciona a la página inicial, actualizándola
	 */
	@GetMapping("/delete")
	public String deleteGame(@RequestParam("name") String name) {
		service.deleteGame(name);
		return("redirect:/");
	}
	
	/**
	 * Método que lleva a una página con una lista de juegos filtrados por el género especificado.
	 * 
	 * @author Jose
	 * @version 1.0, Septiembre 2021
	 * @param genre género por el que se filtrarán los juegos
	 * @param m Model
	 * @return la página filteredGames
	 */
	@GetMapping("/genre")
	public String gamesFilteredByGenre(String genre, Model m) {
		m.addAttribute("gameList", service.gamesFilteredByGenre(genre));
		return "filteredGames";
	}

	/**
	 * Método que llama a la función loadDataFromFile(File) de la capa servicios
	 * 
	 * @author Julio
	 * @version 1.0, Septiembre 2021
	 * @return redirecciona a la página inicial, actualizándola
	 */
	@GetMapping("/loadData")
	public String loadDataFromFile(){
		service.loadDataFromFile(new File("vgsales.csv"));
		return("redirect:/");
	}
}
