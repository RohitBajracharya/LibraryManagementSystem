package com.lms.entities;

public class Student {
	private int sId;
	private String studentName;
	private String branch;
	private String course;
	public Student(int sId, String studentName, String branch, String course) {
		super();
		this.sId = sId;
		this.studentName = studentName;
		this.branch = branch;
		this.course = course;
	}
	public Student() {
		super();
		// TODO Auto-generated constructor stub
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
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	
}
