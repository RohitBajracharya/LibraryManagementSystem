package com.lms.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int bId;
	private String bookName;
	private String authorName;
	private int quantity;
	private String imageUrl;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cid",referencedColumnName = "cid")
	private Category category;
	
	
	public Book() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Book(int bId, String bookName, String authorName, int quantity, String imageUrl) {
		super();
		this.bId = bId;
		this.bookName = bookName;
		this.authorName = authorName;
		this.quantity = quantity;
		this.imageUrl = imageUrl;
	}
	public int getbId() {
		return bId;
	}
	public void setbId(int bId) {
		this.bId = bId;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
	
}
