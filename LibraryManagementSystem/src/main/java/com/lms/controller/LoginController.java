package com.lms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	@GetMapping("/signin")
	public String signin(Model model) {
		model.addAttribute("title","LMS - Login");
		return "login_form";
	}
}
