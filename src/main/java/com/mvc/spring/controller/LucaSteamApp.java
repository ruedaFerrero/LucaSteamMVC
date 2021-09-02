package com.mvc.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mvc.spring.model.Game;
import com.mvc.spring.services.LucaService;

@Controller
public class LucaSteamApp {
	
	@Autowired
	LucaService service;
	
	@GetMapping("/add")
	public String newGame(Model m, Game game) {
		m.addAttribute("game", game);
		return "addGameForm";
	}
	
	@PostMapping("/save")
	public String saveGame(Game game) {
		service.save(game);
		return("redirect:/");
	}
	
	@GetMapping("/edit")
	public String editGame(@RequestParam("name") String name, Model m) {
		m.addAttribute("game", service.findByName(name));
		return "addGameForm";
	}
	
	@GetMapping("/delete")
	public String deleteGame(@RequestParam("name") String name) {
		service.deleteGame(name);
		return("redirect:/");
	}
	
	@GetMapping("/genre")
	public String gamesFilteredByGenre(String genre, Model m) {
		m.addAttribute("gameList", service.gamesFilteredByGenre(genre));
		return "filteredGames";
	}

}
