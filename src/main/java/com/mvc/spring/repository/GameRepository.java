package com.mvc.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mvc.spring.model.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer>{

	List<Game> findAll();
	
	Game findByName(String name);
	
	List<Game> findByGenre(String genre);
}
