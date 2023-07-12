package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.service.AuthorService;
import it.uniroma3.siw.service.BookService;

@Controller
public class AuthorController {
	@Autowired AuthorService authorService;
	@Autowired BookService bookService;
	
	
	@GetMapping("/admin/formNewAuthor")
	public String fromNewAuthor(Model model) {
		model.addAttribute("author",new Author());
		return "admin/formNewAuthor.html";
	}
	
	@PostMapping("admin/Author")
	public String newAuthore(@ModelAttribute("author")Author author,Model model) {
		if(authorService.controlloAuthor(author)){
			this.authorService.save(author);
			model.addAttribute("author",author);
			return "admin/Author.html";
		}
		else {
			if(this.authorService.controlloNomeCognome(author))
			model.addAttribute("messaggioErrore","Questo autore esiste già");
			else
				model.addAttribute("messaggioErrore","Data di nascita dell'autore sbagliata");
			return "admin/formNewAuthor.html";
			
		}
	}
	
	@GetMapping("/admin/authorsToAdd/{idBook}")
	public String getAllAuthors (Model model,@PathVariable("idBook")Long idBook) {
		model.addAttribute("authors",this.authorService.getAll());
		model.addAttribute("book",this.bookService.getById(idBook));
		return "admin/authorsToAdd.html";
	}
	@GetMapping("/admin/Author/{id}")
	public String getAuthor(Model model,@PathVariable("id")Long id) {
		model.addAttribute("author",this.authorService.getById(id));
		return "Author.html";
	}
	
	@GetMapping("/author/{id}")
	public String author(Model model,@PathVariable("id")Long id) {
		model.addAttribute("author",this.authorService.getById(id));
		return "author.html";
	}
	
	@GetMapping("/admin/authors")
	public String allAuthors (Model model) {
		model.addAttribute("authors",this.authorService.getAll());
		return "admin/authors.html";
	}
	@GetMapping("/admin/cancellaAutore/{id}")
	public String cancellaAuthor(Model model,@PathVariable("id")Long id) {
		Author author=this.authorService.getById(id);
		this.bookService.cancellaAutoreDaiLibri(author);
		this.authorService.deleteById(id);
		model.addAttribute("authors",this.authorService.getAll());
		return "admin/authors.html";
	}
}
