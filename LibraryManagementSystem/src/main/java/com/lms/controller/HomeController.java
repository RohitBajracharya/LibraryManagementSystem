package com.lms.controller;

import java.security.Principal;

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

import com.lms.dao.CategoryRepository;
import com.lms.entities.Category;
import com.lms.helper.Message;

@Controller
@RequestMapping("/admin")
public class HomeController {
	@Autowired
	private CategoryRepository categoryRepository;

	@ModelAttribute
	public void commonData(Model model, Principal principal) {
		String username = principal.getName();
		System.out.println("username + " + username);
		model.addAttribute("username", username);
	}

	@GetMapping("/dashboard")
	public String showDashboard(Model model) {
		model.addAttribute("title", "Dashboard");
		return "admin/admin_dashboard";
	}

	@GetMapping("/addCategory")
	public String addCategory(Model model) {
		model.addAttribute("title", "LMS - Add Category");
		model.addAttribute("category", new Category());
		return "admin/add_category";
	}

	@PostMapping("/process-category")
	public String processCategory(@ModelAttribute("category") Category category, Model model, HttpSession session) {
		System.out.println(category);
		
		try {
			this.categoryRepository.save(category);
			session.setAttribute("message", new Message("Category Added successfully", "success"));
		} catch (Exception e) {
			session.setAttribute("message", new Message("Category Failed to Add", "danger"));
		}

		model.addAttribute("title", "LMS - Add Category");
		return "admin/add_category";
	}

}
