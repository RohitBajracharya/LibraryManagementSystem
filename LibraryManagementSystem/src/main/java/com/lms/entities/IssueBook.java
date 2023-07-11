package com.lms.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueBook {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int issueId;
	
	@ManyToOne
	@JoinColumn(name = "bid",referencedColumnName = "bid")
	private Book book;
	
	@ManyToOne
	@JoinColumn(name = "sid",referencedColumnName = "sid")
	private Student student;
	
	@FutureOrPresent(message = "Please Enter todays date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date issueDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Future(message = "Please Enter Future date")
	private Date dueDate;
	
	
}
