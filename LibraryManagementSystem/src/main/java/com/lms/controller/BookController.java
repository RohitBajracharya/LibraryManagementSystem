package com.lms.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.lms.dao.BookRepository;
import com.lms.dao.CategoryRepository;
import com.lms.entities.Book;
import com.lms.entities.Category;
import com.lms.helper.Message;

@Controller
@RequestMapping("/admin")
public class BookController {

	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private CategoryRepository categoryRepository;

	@GetMapping("/addBook")
	public String addBooks(Model model) {

		List<String> category = this.categoryRepository.getAllCategoryName();
		System.out.println("Category: " + category);
		
		model.addAttribute("title", "LMS - Add Book");
		model.addAttribute("book", new Book());
		model.addAttribute("category", category);
		return "admin/add_book";
	}

	@PostMapping("/processBook")
	public String processBook(@ModelAttribute Book book, @RequestParam("categoryName") String categoryName,
			@RequestParam("image") MultipartFile image, HttpSession session) {
		try {
			if (image.isEmpty()) {
				System.out.println("File is empty");
				book.setImageUrl("defaultBook.jpg");
			} else {
				book.setImageUrl(image.getOriginalFilename());
				java.io.File filepath = new ClassPathResource("static/img").getFile();
				Path path = Paths
						.get(filepath.getAbsolutePath() + java.io.File.separator + image.getOriginalFilename());
				Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				System.out.println("image is uploaded");
			}
			Category category = this.categoryRepository.findByCategoryName(categoryName);
			book.setCategory(category);
			this.bookRepository.save(book);
			int quantity = updateNoOfBook(category.getCid());
			System.out.println(quantity);
			category.setNoOfBooks(quantity);
			this.categoryRepository.save(category);
			session.setAttribute("message", new Message("Book Added Successfully", "success"));
		} catch (

		Exception e) {
			session.setAttribute("message", new Message("Something went wrong! Try Again", "danger"));
		}
		return "admin/add_book";
	}

	@GetMapping("/showBooks/{page}")
	public String showBooks(@PathVariable("page") Integer page, Model model) {
		Pageable pageable = PageRequest.of(page, 5);
		Page<Book> book = this.bookRepository.findAllBook(pageable);
		System.out.println("book:"+book);
		model.addAttribute("title", "LMS - Show Category");
		model.addAttribute("book", book);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", book.getTotalPages());
		return "admin/show_books";
	}

	public int updateNoOfBook(int cid) {
		int quantity = this.bookRepository.getQuantityByCategory(cid);
		return quantity;
	}

}
