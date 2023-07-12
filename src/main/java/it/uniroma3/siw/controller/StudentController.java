package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Student;
import it.uniroma3.siw.service.StudentService;

@Controller
public class StudentController {
	@Autowired 
	private StudentService studentService;

	@GetMapping("/admin/formNewStudent")
	public String getStudent(Model model) {
		model.addAttribute("student",new Student());

		return "admin/formNewStudent.html";
	}
	@PostMapping("/admin/formNewStudent")
	public String saveStudent(@ModelAttribute("student")Student student,Model model) {
		if(this.studentService.controlloStudent(student)) {
			this.studentService.save(student);
			model.addAttribute("student",student);
			return "admin/Student.html";
		}
		else {
			if(this.studentService.controlloMatricola(student))
				model.addAttribute("messaggioErrore","Questo studente esiste già");
			else
				model.addAttribute("messaggioErrore","Anno di nascita sbagliato");
			return "admin/formNewStudent.html";

		}


	}
	@GetMapping("/admin/student/{id}")
	public String getStudentbyId (Model model,@PathVariable("id")Long matricola) {
		model.addAttribute("student",this.studentService.getById(matricola));
		return ("admin/Student.html");
	}

	@GetMapping("/admin/students")
	public String getStudents(Model model) {
		model.addAttribute("students",this.studentService.getAll());
		return ("admin/Students.html");
	}

	@GetMapping("/admin/deleteStudent")
	public String deleteBook(Model model,@RequestParam Long id) {
//		 cancello user e credenziali quindi student diventa null
			if(!(this.studentService.controlloPrestiti(id))) {
				this.studentService.cancelloUser(id);
				model.addAttribute("students",this.studentService.getAll());
				return "admin/students.html";
			}
			else 
				return "admin/prestitoError.html";
	}
}
