package com.onlinefood.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlinefood.model.Product;

public interface ProductRepository extends JpaRepository<Product,Integer> 
{
	Optional<Product> findProductById(int id);
}
