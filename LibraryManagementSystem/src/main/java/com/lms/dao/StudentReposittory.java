package com.lms.dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lms.entities.Student;

public interface StudentReposittory extends JpaRepository<Student, Integer>{

	@Query("select s from Student s")
	public Page<Student> findAllStudent(Pageable pageable);
	
	public Student findByStudentName(String studentName);
}

