package com.category.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.category.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

	public Category findByName(String name);
	
	public Category findByAlias(String alias);
	
	
	
}
