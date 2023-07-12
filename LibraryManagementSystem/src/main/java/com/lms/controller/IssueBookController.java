package com.lms.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
			@RequestParam("bookName") String bookName, @RequestParam("studentName") String studentName, Model model,
			HttpSession session) {
		model.addAttribute("title", "LMS - Issue Book");
		if (result.hasErrors()) {
			session.setAttribute("message", new Message("Book Failed to Issues", "danger"));
			System.out.println("error " + result);
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
			session.setAttribute("message", new Message("Book Issued Successfully", "success"));
		} catch (Exception e) {
			session.setAttribute("message", new Message("Book Failed to Issues", "danger"));
		}
		System.out.println(issueBook.toString());
		return "redirect:/admin/issue-book";
	}

//	handler to show issue Book details
	@GetMapping("/show-issue-books/{page}")
	public String showIssueBooks(Model model, @PathVariable("page") Integer page) {
		Pageable pageable = PageRequest.of(page, 5);
		Page<IssueBook> issueBook = this.issueBookRepository.findAllIssueBook(pageable);
		model.addAttribute("title", "LMS - Issue Book");
		model.addAttribute("issueBook", issueBook);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", issueBook.getTotalPages());
		return "admin/show_issue_books";
	}

//	handler to delete issue book
	@PostMapping("/delete-issue-book/{issueId}")
	public String deleteIssueBook(@PathVariable("issueId") int issueId, HttpSession session) {
		try {

			this.issueBookRepository.deleteById(issueId);
			session.setAttribute("message", new Message("Issue Book Deleted Successfully", "success"));
		} catch (Exception e) {
			session.setAttribute("message", new Message("Issue Book Failed to Delete", "danger"));
		}
		return "redirect:/admin/show-issue-books/0";
	}

//	handler to show edit issue book form
	@PostMapping("/edit-issue-book/{issueId}")
	public String editIssueBook(@PathVariable("issueId") int issueId, Model model) {
		List<Book> book = this.bookRepository.findAll();
		List<Student> student = this.studentReposittory.findAll();
		IssueBook issueBook = this.issueBookRepository.findById(issueId).get();
		model.addAttribute("title", "LMS - Edit Issue Book");
		model.addAttribute("issueBook", issueBook);
		model.addAttribute("book", book);
		model.addAttribute("student", student);
		return "admin/edit_issue_book";
	}

//	handler to update issue book
	@PostMapping("/update-issue-book")
	public String updateIssueBook(@Valid @ModelAttribute IssueBook issueBook, BindingResult result, HttpSession session,
			Model model, @RequestParam("bookName") String bookName, @RequestParam("studentName") String studentName) {
		if (result.hasErrors()) {
			session.setAttribute("message", new Message("Book Failed to Issued", "danger"));
			System.out.println("error " + result);
			List<Book> book = this.bookRepository.findAll();
			List<Student> student = this.studentReposittory.findAll();
			model.addAttribute("issueBook", issueBook);
			model.addAttribute("book", book);
			model.addAttribute("student", student);
			return "admin/edit_issue_book";
		} else {
			try {
				IssueBook oldIssueBook = this.issueBookRepository.findById(issueBook.getIssueId()).get();
				Book book = this.bookRepository.findByBookName(bookName);
				oldIssueBook.setBook(book);
				Student student = this.studentReposittory.findByStudentName(studentName);
				oldIssueBook.setStudent(student);
				oldIssueBook.setIssueDate(issueBook.getIssueDate());
				oldIssueBook.setDueDate(issueBook.getDueDate());
				this.issueBookRepository.save(oldIssueBook);
				session.setAttribute("message", new Message("Issue Book Detail Successfully Updated", "success"));

			} catch (Exception e) {
				e.printStackTrace();
				session.setAttribute("message", new Message("Issue Book Detail Failed to Update", "danger"));
			}
		}

		return "redirect:/admin/show-issue-books/0";
	}

}
