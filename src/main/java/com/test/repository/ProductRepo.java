package com.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product,Integer> {

}
