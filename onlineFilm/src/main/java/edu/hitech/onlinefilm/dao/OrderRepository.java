package edu.hitech.onlinefilm.dao;

import edu.hitech.onlinefilm.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomId(Integer customId);
    Page<Order> findByCustomIdOrderByOrderTimeDesc(Integer customId, Pageable pageable);
}