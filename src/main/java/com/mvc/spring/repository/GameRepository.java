package com.mvc.spring.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mvc.spring.model.Game;

/**
 * GameRepository
 * La interfaz GameRepository nos facilita la interacción objetos-BBDD funcionando como pieza intermedia
 * @author Jose Antonio
 * @version 1.0, Septiembre 2021
 */
@Repository
public interface GameRepository extends JpaRepository<Game, Integer>{

	/**
	 * Devuelve una lista con todos los juegos
	 * @return Lista de juegos
	 */
	List<Game> findAll();

	/**
	 * Busca un juego por su nombre, en caso de haber varios devuelve el primer juego encontrado
	 * @param name Nombre del juego
	 * @return El juego si ha sido encontrado, null si no ha sido encontrado
	 */
	Game findByName(String name);


	/**
	 * Devuelve una lista de juegos filtrados por su género
	 * @param genre Género de los juegos que queremos buscar
	 * @return Lista de juegos
	 */
	List<Game> findByGenre(String genre);
}
