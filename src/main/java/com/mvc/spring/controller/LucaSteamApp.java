package com.mvc.spring.controller;

import com.mvc.spring.model.Game;
import com.mvc.spring.model.QueryInfo;
import com.mvc.spring.services.LucaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * LucaSteamApp
 * Controlador de la aplicación. Permite dirigir al usuario a través de las distintas páginas del proyecto y actualizarlas.
 *
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
     * @param m    Model
     * @param game objeto tipo Game vacío
     * @return la página UserForm
     * @author Jose
     * @version 1.0, Septiembre 2021
     */
    @GetMapping("/add")
    public String newGame(Model m, Game game) {
        m.addAttribute("game", game);
        return "GameForm";
    }

    //Get route of the search page
    @GetMapping("/search")
    public String InitsearchGame(Model m) {
        m.addAttribute("query", new QueryInfo());
        return "SearchForm";
    }

    //Post route of the search page, gets and process the query
    @PostMapping("/search")
    public String processSearchGame(QueryInfo query, Model m) {
        if(query.getText().equals(""))
            return ("redirect:/search");

        System.out.println("###########");
        System.out.println("text: " + query.getText() + " option: " + query.getOption());
        System.out.println("###########");
        List<Game> listGames = null;
        switch (query.getOption()) {
            case "name":
                listGames = service.gamesFilteredByName(query.getText());
                break;
            case "genre":
                listGames = service.gamesFilteredByGenre(query.getText());
                break;
            case "platform":
                listGames = service.gamesFilteredByPlatform(query.getText());
                break;
            case "publisher":
                listGames = service.gamesFilteredByPublisher(query.getText());
                break;
            case "year":
                Integer year = 0;
                try {
                    year = Integer.parseInt(query.getText());
                } catch (java.lang.NumberFormatException e) {
                    year = null;
                }
                listGames = service.gamesFilteredByYear(year);
                break;
            case "eu_sales":
                Double sales = 0.0;
                try {
                    sales = Double.parseDouble(query.getText());
                } catch (java.lang.NumberFormatException e) {
                    sales = null;
                }

                listGames = service.gamesFilteredByEusales(sales);
                break;

        }
        m.addAttribute("query", query);
        m.addAttribute("listGames", listGames);
        return "SearchForm";
    }

    /**
     * Método que actualiza la tabla de datos guardando un juego en ella, ya sea añadiéndolo como un juego nuevo o actualizando uno ya existente.
     *
     * @param game objeto tipo Game a guardar
     * @return redirecciona a la página inicial, actualizándola
     * @author Jose
     * @version 1.0, Septiembre 2021
     */
    @PostMapping("/save")
    public String saveGame(Game game) {
        if(game.getEuSales() != null && game.getYear() != null && !game.getGenre().equals("") && !game.getName().equals("") && !game.getPlatform().equals("") && !game.getPublisher().equals(""))
            service.save(game);
        return ("redirect:/");
    }

    /**
     * Método que dirige al formulario para editar la información de un juego tomando el nombre del juego a modificar.
     *
     * @param id nombre del juego a editar
     * @param m    Model
     * @return la página addGameForm
     * @author Jose
     * @version 1.0, Septiembre 2021
     */
    @GetMapping("/edit")
    public String editGame(@RequestParam("id") Long id, Model m) {
        m.addAttribute("game", service.findById(id));
        return "GameForm";
    }

    /**
     * Método que llama a la función deleteGame(String) de la capa servicios.
     *
     * @param id nombre del juego a eliminar
     * @return redirecciona a la página inicial, actualizándola
     * @author Jose
     * @version 1.0, Septiembre 2021
     */
    @PostMapping("/delete")
    public String deleteGame(Long id) {
        if (id != null)
            service.deleteGame(id);
        return ("redirect:/");

    }

    /**
     * Método que lleva a una página con una lista de juegos filtrados por el género especificado.
     *
     * @param genre género por el que se filtrarán los juegos
     * @param m     Model
     * @return la página filteredGames
     * @author Jose
     * @version 1.0, Septiembre 2021
     */
    @GetMapping("/genre")
    public String gamesFilteredByGenre(String genre, Model m) {
        m.addAttribute("gameList", service.gamesFilteredByGenre(genre));
        return "filteredGames";
    }

    /**
     * Método que llama a la función loadDataFromFile(File) de la capa servicios
     *
     * @return redirecciona a la página inicial, actualizándola
     * @author Julio
     * @version 1.0, Septiembre 2021
     */
    @GetMapping("/loadData")
    public String loadDataFromFile() {
        service.loadDataFromFile(new File("vgsales.csv"));
        return ("redirect:/");
    }

    /**
     * Carga la página principal
     *
     * @param model
     * @return index.html
     */
    @GetMapping
    public String mainPage(@RequestParam Map<String, Object> param, Model model) {
        int page = param.get("page") != null ? Integer.valueOf(param.get("page").toString()) - 1 : 0;

        PageRequest pr = PageRequest.of(page, 30);

        Page<Game> pageGame = service.getAll(pr);

        int totalPages = pageGame.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pages = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pages", pages);
        }

        model.addAttribute("gameTotal", service.getAllGames());
        model.addAttribute("gameList", pageGame.getContent());
        model.addAttribute("current", page + 1);
        model.addAttribute("prev", page);
        model.addAttribute("next", page + 2);
        model.addAttribute("last", totalPages);
        return "index";
    }

    /**
     * Carga la página que muestra todos los editores
     * @param model
     * @return
     */
    @GetMapping("/publishers")
    public String getPublishers(Model model){
        model.addAttribute("publisherList", service.getAllPublishers());
        return "editorList";
    }
}