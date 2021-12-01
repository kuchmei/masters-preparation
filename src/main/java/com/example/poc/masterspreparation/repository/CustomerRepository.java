package com.example.poc.masterspreparation.repository;

import com.example.poc.masterspreparation.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

        Customer findByEmail (String email);
}