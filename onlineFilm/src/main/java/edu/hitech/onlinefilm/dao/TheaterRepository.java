package edu.hitech.onlinefilm.dao;

import edu.hitech.onlinefilm.domain.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheaterRepository extends JpaRepository<Theater, Integer> {
    // You can add custom query methods here if needed
}