package com.lms.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lms.dao.BookRepository;
import com.lms.dao.IssueBookRepository;
import com.lms.dao.StudentReposittory;
import com.lms.entities.Book;
import com.lms.entities.IssueBook;
import com.lms.entities.Student;
import com.lms.helper.Message;

@Controller
@RequestMapping("/admin")
public class IssueBookController {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private StudentReposittory studentReposittory;
	
	@Autowired
	private IssueBookRepository issueBookRepository;
	
	

//	handler to display issue book form
	@GetMapping("/issue-book")
	public String issueBook(Model model) {
		List<Book> book = this.bookRepository.findAll();
		List<Student> student = this.studentReposittory.findAll();
		model.addAttribute("title", "LMS - Issue Book");
		model.addAttribute("book", book);
		model.addAttribute("student", student);
		model.addAttribute("issueBook", new IssueBook());

		return "admin/issue_book";
	}

	@PostMapping("/process-issue-book")
	public String processIssueBook(@Valid @ModelAttribute("issueBook") IssueBook issueBook, BindingResult result,
			@RequestParam("bookName") String bookName, @RequestParam("studentName") String studentName,Model model,HttpSession session) {
		model.addAttribute("title", "LMS - Issue Book");
		if (result.hasErrors()) {
			session.setAttribute("message",new Message("Book Failed to Issues", "danger"));
			System.out.println("error "+result);
			List<Book> book = this.bookRepository.findAll();
			List<Student> student = this.studentReposittory.findAll();
			model.addAttribute("book", book);
			model.addAttribute("student", student);
			return "admin/issue_book";
		}
		try {
			Book book = this.bookRepository.findByBookName(bookName);
			Student student = this.studentReposittory.findByStudentName(studentName);
			issueBook.setBook(book);
			issueBook.setStudent(student);
			issueBookRepository.save(issueBook);
			session.setAttribute("message",new Message("Book Issued Successfully", "success"));
		} catch (Exception e) {
			session.setAttribute("message",new Message("Book Failed to Issues", "danger"));
		}
		System.out.println(issueBook.toString());
		return "redirect:/admin/issue-book";
	}
}
