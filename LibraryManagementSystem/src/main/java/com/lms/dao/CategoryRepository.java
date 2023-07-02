package com.lms.dao;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lms.entities.Category;


public interface CategoryRepository extends JpaRepository<Category, Integer>{

	@Query("Select c from Category c")
	public Page<Category> findAllCategory(Pageable pageable);
	
	@Query("Select c.categoryName from Category c")
	public List<String> getAllCategoryName();

	public Category findByCategoryName(String name);
	
}
