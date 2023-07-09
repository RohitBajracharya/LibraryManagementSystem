package com.lms.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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

import com.lms.dao.StudentReposittory;
import com.lms.entities.Student;
import com.lms.helper.Message;

@Controller
@RequestMapping("/admin")
public class StudentController {

	@Autowired
	private StudentReposittory studentReposittory;

//	handler to open add student form
	@GetMapping("/add-student")
	public String addStudent(Model model) {
		model.addAttribute("title", "LMS - Add Student");
		model.addAttribute("student", new Student());
		return "admin/add_student";
	}

//	handler to process add student
	@PostMapping("/process-student")
	public String processStudent(@ModelAttribute Student student, @RequestParam("faculty") String faculty,
			@RequestParam("semester") String semester, @RequestParam("image") MultipartFile image,
			HttpSession session) {
		System.out.println("student " + student.getStudentName());
		try {
			if (image.isEmpty()) {
				System.out.println("File is empty");
				student.setImageUrl("defaultStudent.jpg");
			} else {
				student.setImageUrl(image.getOriginalFilename());
				java.io.File filepath = new ClassPathResource("static/img").getFile();
				Path path = Paths
						.get(filepath.getAbsolutePath() + java.io.File.separator + image.getOriginalFilename());
				Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				System.out.println("image is uploaded");
			}
			student.setFaculty(faculty);
			student.setSemester(semester);
			this.studentReposittory.save(student);
			session.setAttribute("message", new Message("Student Added Successfully", "success"));

		} catch (Exception e) {
			session.setAttribute("message", new Message("Failed to Add Student", "danger"));
		}

		return "redirect:/admin/show-student/0";
	}

//	handler to show all students
	@GetMapping("/show-student/{page}")
	public String showStudent(@PathVariable("page") Integer page, Model model) {
		Pageable pageable = PageRequest.of(page, 5);
		Page<Student> student = this.studentReposittory.findAllStudent(pageable);

		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", student.getTotalPages());
		model.addAttribute("student", student);
		model.addAttribute("title", "LMS - Show Student");
		return "admin/show_students";
	}

//	handler to delete student
	@PostMapping("/delete-student/{sId}")
	public String deleteStudent(@PathVariable("sId") int sId, HttpSession session) {
		try {
			this.studentReposittory.deleteById(sId);
			session.setAttribute("message", new Message("Student Deleted Successfully", "success"));
		} catch (Exception e) {
			session.setAttribute("message", new Message("Failed to Delete Student", "danger"));
		}
		return "redirect:/admin/show-student/0";
	}

//	handler to show edit student form
	@PostMapping("/edit-student/{sId}")
	public String editStudent(@PathVariable("sId") int sId, Model model) {
		Student student = this.studentReposittory.findById(sId).get();
	
		model.addAttribute("student", student);
		model.addAttribute("title", "LMS - Edit Student");
		return "admin/edit_student";
	}

	@PostMapping("/update-student")
	public String updateStudent(@ModelAttribute Student student, @RequestParam("image") MultipartFile file,HttpSession session,
			@RequestParam("faculty") String faculty, @RequestParam("semester") String semester) {
		Student oldStudent = this.studentReposittory.findById(student.getsId()).get();
		try {
			oldStudent.setStudentName(student.getStudentName());
			oldStudent.setFaculty(faculty);
			oldStudent.setSemester(semester);
			if (!file.isEmpty()) {
				File deleteFile = new ClassPathResource("static/img").getFile();
				File oldImageFile = new File(deleteFile, oldStudent.getImageUrl());
				oldImageFile.delete();
				File filePath = new ClassPathResource("static/img").getFile();
				Path path = Paths.get(filePath.getAbsolutePath() + File.separator + file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				oldStudent.setImageUrl(file.getOriginalFilename());

			} else {
				oldStudent.setImageUrl(oldStudent.getImageUrl());
			}
			this.studentReposittory.save(oldStudent);
			session.setAttribute("message", new Message("Student Updated Successfully", "success"));
		} catch (Exception e) {
			session.setAttribute("message", new Message("Failed to Update Student", "danger"));
		}
		return "redirect:/admin/show-student/0";
	}
}
