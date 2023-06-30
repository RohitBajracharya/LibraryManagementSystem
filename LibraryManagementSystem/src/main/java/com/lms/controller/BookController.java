package com.lms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class BookController {

	@GetMapping("/addBook")
	public String addBooks(Model model) {
		model.addAttribute("title","LMS - Add Book");
		return "admin/add_book";
	}
}
