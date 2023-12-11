package edu.hitech.onlinefilm.dao;

import edu.hitech.onlinefilm.domain.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository extends JpaRepository<Film, Integer> {
    // You can add custom query methods here if needed
    // For example, find films by director, classify, etc.
}