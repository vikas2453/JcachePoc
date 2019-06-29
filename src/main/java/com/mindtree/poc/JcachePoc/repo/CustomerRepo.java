package com.mindtree.poc.JcachePoc.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mindtree.poc.JcachePoc.model.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {

}
