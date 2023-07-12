package it.uniroma3.siw.controller;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Prestito;
import it.uniroma3.siw.model.Student;
import it.uniroma3.siw.service.BookService;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.PrestitoService;
import it.uniroma3.siw.service.StudentService;
import it.uniroma3.siw.model.User;



@Controller
public class PrestitoController {

	@Autowired
	private BookService bookService;
	@Autowired
	private PrestitoService prestitoService;
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired	
	private StudentService studentService;


	@GetMapping(value="/indexPrestiti")
	public String indexPrestiti(Model model) {
		return "indexPrestiti.html";
	}

	@GetMapping(value="/addPrestito")
	public String addPrestito(Model model) {
		model.addAttribute("books",bookService.getAllNonInPrestito());
		return "addPrestito.html";
	}

	@PostMapping("/prestito/{id}/{username}")
	public String formPrestito(@ModelAttribute("prestito") Prestito prestito,
			@PathVariable("id") Long idBook,@PathVariable("username") String username, Model model) {

		Book book=bookService.getById(idBook);
		Credentials credentials= credentialsService.getCredentials(username);
		User user= credentials.getUser();
		
		if(this.prestitoService.controlloPrestito(prestito,user)) {
			this.prestitoService.addPrestito(prestito,user,book);
			return "prestitoRiuscito.html";
		} 
		else {
			model.addAttribute("messaggioErrore", "Date errate");
			return "formPrestito.html";

		}
	}


	@GetMapping(value="/formPrestito/{id}/{username}")
	public String newPrestito(@ModelAttribute("prestito") Prestito prestito,
			@PathVariable("id") Long idBook,@PathVariable("username") String username, Model model) {

		model.addAttribute("prestito", new Prestito());
		return "formPrestito.html";
	}


	@GetMapping(value="/visualizzaPrestiti/{username}")
	public String visualizzaPrestitiAttivi(@PathVariable("username") String username,Model model) {
		Credentials credentials= credentialsService.getCredentials(username);
		User user= credentials.getUser();
		Student student= studentService.getById(user.getMatricolaStudente());
		if(student!=null) {
			model.addAttribute("prestitiAttivi", prestitoService.getPrestitiAttivi(student));
			model.addAttribute("prestitiConclusi",prestitoService.getPrestitiConclusi(student));
			return "visualizzaPrestiti.html";
		}
		else
			return "indexPrestiti.html";
	}
	
	@GetMapping(value="/admin/prestiti")
	public String prestiti(Model model) {
		model.addAttribute("prestitiAttivi",prestitoService.getAllPrestitiAttivi());
		model.addAttribute("prestitiConclusi",prestitoService.getAllPrestitiConclusi());
		return "admin/cancellaPrestiti.html";
	}
	
	@GetMapping(value="/admin/cancellaPrestito/{id}")
	public String cancellaPrestito(@PathVariable("id")Long id,Model model ) {
		this.prestitoService.deleteById(id);
		model.addAttribute("prestitiAttivi",prestitoService.getAllPrestitiAttivi());
		model.addAttribute("prestitiConclusi",prestitoService.getAllPrestitiConclusi());
		return "admin/cancellaPrestiti.html";
	}
}
