package com.mvc.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mvc.spring.model.Game;
import com.mvc.spring.services.LucaService;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * LucaSteamApp
 * Controlador de la aplicación. Permite dirigir al usuario a través de las distintas páginas del proyecto y actualizarlas. 
 * @author Julio
 * @version 1.0, Septiembre 2021
 */
@Controller
public class LucaSteamApp {
	
	@Autowired
	private LucaService service;
	
	/**
	 * Método que lleva a la página con el formulario para añadir un juego.
	 * 
	 * @author Jose
	 * @version 1.0, Septiembre 2021
	 * @param m Model
	 * @param game objeto tipo Game vacío
	 * @return la página UserForm
	 */
	@GetMapping("/add")
	public String newGame(Model m, Game game) {
		m.addAttribute("game", game);
		return "GameForm";
	}
        
        @GetMapping("/search")
	public String InitsearchGame(Game game) {
		return "SearchForm";
	}
        
        
        @PostMapping("/search")
	public String processSearchGame(@RequestParam("name") String name,Model m) {
                m.addAttribute("gameList", service.findByName(name));
		return "SearchForm";
	}
        
        
	
	/**
	 * Método que actualiza la tabla de datos guardando un juego en ella, ya sea añadiéndolo como un juego nuevo o actualizando uno ya existente.
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
	public String editGame(@RequestParam("id") Long id, Model m) {
		m.addAttribute("game", service.findById(id));
		return "GameForm";
	}
	
	/**
	 * Método que llama a la función deleteGame(String) de la capa servicios.
	 * 
	 * @author Jose
	 * @version 1.0, Septiembre 2021
	 * @param name nombre del juego a eliminar
	 * @return redirecciona a la página inicial, actualizándola
	 */
	@PostMapping("/delete")
	public String deleteGame(Long id) {
		if(id!=null)
		service.deleteGame(id);
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

	/**
	 * Carga la página principal
	 * @param model
	 * @return index.html
	 */
	@GetMapping
	public String mainPage(@RequestParam Map<String, Object> param, Model model){			
		int page = param.get("page") != null ? Integer.valueOf(param.get("page").toString()) -1 : 0;
		
		PageRequest pr = PageRequest.of(page, 30);
		
		Page<Game> pageGame = service.getAll(pr);
		
		int totalPages = pageGame.getTotalPages();
		if(totalPages>0) {
			List<Integer> pages = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pages", pages);
		}
		
		model.addAttribute("gameTotal", service.getAllGames());
		model.addAttribute("gameList", pageGame.getContent());
		model.addAttribute("current", page +1);
		model.addAttribute("prev", page);
		model.addAttribute("next", page +2);
		model.addAttribute("last", totalPages);
		
		
		return "index";
	}
	

}