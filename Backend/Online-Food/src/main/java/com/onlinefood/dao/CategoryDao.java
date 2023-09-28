package com.onlinefood.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlinefood.model.Category;

public interface CategoryDao extends JpaRepository<Category, Integer> {
	

}
