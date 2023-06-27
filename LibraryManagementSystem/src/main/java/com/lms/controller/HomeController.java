package com.lms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class HomeController {

	@GetMapping("/dashboard")
	public String showDashboard(Model model) {
		model.addAttribute("title","Dashboard");
		return "admin/admin_dashboard";
	}
	@GetMapping("/login")
	public String login(Model model) {
		return "admin/login_form";
	}
}
