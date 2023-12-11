package edu.hitech.onlinefilm.dao;

import edu.hitech.onlinefilm.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByAccount(String account);
    // You can add custom query methods here if needed
    // For example, find customers by account, email, etc.
}