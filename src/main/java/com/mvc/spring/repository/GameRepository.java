package com.mvc.spring.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mvc.spring.model.Game;

/**
 * GameRepository
 * La interfaz GameRepository nos facilita la interacción objetos-BBDD funcionando como pieza intermedia
 * @author Jose Antonio
 * @version 1.0, Septiembre 2021
 */
@Repository
public interface GameRepository extends JpaRepository<Game, Long>{

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
	List<Game> findAllByGenreContaining(String genre);

	/**
	 * Devuelve una lista de juegos filtrados por su nombre
	 * @param name Nombre completo o parte del nombre de los juegos que buscamos
	 * @return Lista de juegos
	 */
	List<Game> findAllByNameContaining(String name);

	/**
	 * Devuelve una lista de juegos filtrados por su plataforma
	 * @param name Nombre completo o parte del nombre de la plataforma
	 * @return Lista de juegos
	 */
	List<Game> findAllByPlatformContaining(String name);

	/**
	 * Devuelve una lista de juegos filtrados por su publisher
	 * @param name Nombre completo o parte del nombre del publisher
	 * @return Lista de juegos
	 */
    List<Game> findAllByPublisherContaining(String name);

	/**
	 * Devuelve una lista de juegos filtrados por su año de lanzamiento
	 * @param year Año de lanzamiento
	 * @return Lista de juegos
	 */
    List<Game> findAllByYear(Integer year);

	/**
	 * Devuelve una lista de juegos filtrados por sus ventas
	 * @param sales Número de ventas del juego
	 * @return Lista de juegos
	 */
    List<Game> findAllByEuSales(Double sales);

	/**
	 * Devuelve la lista de todos las editoras
	 * @return Lista de editoras
	 */
    @Query("SELECT distinct publisher from Game")
    List<String> findAllPublishers();
}
