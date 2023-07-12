package com.lms.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lms.entities.IssueBook;

public interface IssueBookRepository extends JpaRepository<IssueBook, Integer>{

	@Query("Select i from IssueBook i")
	public Page<IssueBook> findAllIssueBook(Pageable pageable);
}
