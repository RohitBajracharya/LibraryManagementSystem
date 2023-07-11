package com.lms.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.entities.IssueBook;

public interface IssueBookRepository extends JpaRepository<IssueBook, Integer>{

}
