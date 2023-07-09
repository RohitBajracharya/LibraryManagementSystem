package com.lms.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int sId;
	private String studentName;
	private String faculty;
	private String semester;
	private String imageUrl;
	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Student(int sId, String studentName, String faculty, String semester, String imageUrl) {
		super();
		this.sId = sId;
		this.studentName = studentName;
		this.faculty = faculty;
		this.semester = semester;
		this.imageUrl = imageUrl;
	}

	public int getsId() {
		return sId;
	}
	public void setsId(int sId) {
		this.sId = sId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getFaculty() {
		return faculty;
	}
	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
}
