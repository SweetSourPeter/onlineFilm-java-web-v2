package edu.hitech.onlinefilm.dao;

import edu.hitech.onlinefilm.domain.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatisticsRepository extends JpaRepository<Statistic, Integer> {
    Optional<Statistic> findByScheduleId(Integer scheduleId);
    // You can add custom query methods here if needed
}