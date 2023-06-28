package com.lms.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
