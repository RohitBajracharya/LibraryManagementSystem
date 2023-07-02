package com.lms.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lms.entities.Book;

public interface BookRepository extends JpaRepository<Book	, Integer>{

    @Query("SELECT SUM(b) FROM Book b WHERE b.category.cid = :categoryId")
	public int getQuantityByCategory(@Param("categoryId") int categoryId);
    
    @Query("Select b from Book b")
	public Page<Book> findAllBook(Pageable pageable);

}
