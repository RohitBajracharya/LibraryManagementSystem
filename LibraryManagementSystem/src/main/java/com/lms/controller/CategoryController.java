package com.lms.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.lms.dao.CategoryRepository;
import com.lms.entities.Category;
import com.lms.helper.Message;

@Controller
@RequestMapping("/admin")
public class CategoryController {
	@Autowired
	private CategoryRepository categoryRepository;

	@ModelAttribute
	public void commonData(Model model, Principal principal) {
		String username = principal.getName();
		System.out.println("username + " + username);
		model.addAttribute("username", username);
	}

	//handler to show add category form	
	@GetMapping("/addCategory")
	public String addCategory(Model model) {
		model.addAttribute("title", "LMS - Add Category");
		model.addAttribute("category", new Category());
		return "admin/add_category";
	}

	//handler to save category into database	
	@PostMapping("/process-category")
	public String processCategory(@ModelAttribute("category") Category category, Model model, HttpSession session) {
		System.out.println(category);
		
		try {
			this.categoryRepository.save(category);
			session.setAttribute("message", new Message("Category Added successfully", "success"));
		} catch (Exception e) {
			session.setAttribute("message", new Message("Category Failed to Add", "danger"));
		}

		model.addAttribute("title", "LMS - Show Category");
		return "redirect:/admin/show-category/0";
	}
	
	//handler to show data present in database in table	
	@GetMapping("/show-category/{page}")
	public String showCategory(@PathVariable("page") Integer page,Model model) {
		Pageable pageable = PageRequest.of(page, 5);
		Page<Category> category = this.categoryRepository.findAllCategory(pageable);
		model.addAttribute("title","LMS - Show Category");
		model.addAttribute("category",category);
		model.addAttribute("currentPage",page);
		model.addAttribute("totalPages",category.getTotalPages());
		return "admin/show_category";
	}
	
	//handler to delete category	
	@PostMapping("/delete-category/{cid}")
	public String deleteCategory(@PathVariable("cid")Integer cid,HttpSession session) {
		try {
			this.categoryRepository.deleteById(cid);
			session.setAttribute("message", new Message("Category Deleted Successfully", "success"));
		} catch (Exception e) {
			session.setAttribute("message", new Message("Category Failed to Delete", "d"));
		}
		
		return "redirect:/admin/show-category/0";
	}
	
	//handler to show edit category form	
	@PostMapping("/edit-category/{cid}")
	public String editCategory(@PathVariable("cid") Integer cid,Model model) {
		Category category = this.categoryRepository.findById(cid).get();
		model.addAttribute("category",category);
		model.addAttribute("title","LMS - Edit Category");
		return "admin/edit_category";
	}
	
	//handler to update category	
	@PostMapping("/update-category")
	public String updateCategory(@ModelAttribute Category category,HttpSession session,Model model) {
		model.addAttribute("title","LMS - Add Category");
		Category category2 = this.categoryRepository.findById(category.getCid()).get();
		try {
			System.out.println("name "+category.getCategoryName()+" section "+category.getSection());
			
			category2.setCategoryName(category.getCategoryName());
			category2.setSection(category.getSection());
			this.categoryRepository.save(category2);
			session.setAttribute("message", new Message("Category Successfully Updated", "success"));
		} catch (Exception e) {
			session.setAttribute("message", new Message("Category Failed to Update", "success"));
		}
		return "redirect:/admin/show-category/0";
	}
}
