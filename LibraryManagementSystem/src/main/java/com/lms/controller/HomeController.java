package com.lms.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class HomeController {

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


}
