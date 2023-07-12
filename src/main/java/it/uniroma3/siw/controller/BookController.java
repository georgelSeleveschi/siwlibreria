package it.uniroma3.siw.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.service.BookService;

import java.io.IOException;
import javax.transaction.Transactional;

@Controller
public class BookController {
	
	@Autowired BookService bookService;

	
	
	
	@GetMapping("/index")
	public String index() {
		return "index.html";
	}
	
	@GetMapping("/admin/formNewBook")
	public String fromNewBook(Model model) {
		model.addAttribute("book",new Book());
		return "admin/formNewBook.html";
	}

	@PostMapping("/admin/libri")
	public String newMovie (@ModelAttribute("book")Book book,Model model,@RequestParam("image") MultipartFile file) throws IOException {
		if(!bookService.existsBookByTitleAndIsbn(book.getTitle(),book.getIsbn())) {
			this.bookService.newBook(file,book);
			model.addAttribute("book",book);
			return "Book.html";
		}
		else {
			model.addAttribute("messaggioErrore","Questo libro esiste già");
			return "admin/formNewBook.html";
			
		}


	}
	@GetMapping("admin/libri/{Id}")
	public String getBook (@PathVariable("Id")Long id , Model model) {
		model.addAttribute("book",this.bookService.getById(id));
		return "admin/Book.html";
	}
	@GetMapping("/admin/libri")
	public String libri (Model model) {
		model.addAttribute("libri",this.bookService.getAll());
		return "admin/libri.html";
	}
	@GetMapping ("/formSearchTitle")
	public String formSearchTitle() {
		return "formSearchTitle.html";
	}
	@PostMapping ("/searchTitle")
	public String searchTitle (Model model,@RequestParam String title) {
		model.addAttribute("libri",this.bookService.getByTitle(title));
		return "foundBooks.html";}

	@GetMapping ("/formSearchISBN")
	public String formSearchBook() {
		return "formSearchisbn.html";
	}
	@PostMapping("/searchBooks")
	public String searchBooks(Model model,@RequestParam String isbn) {
		model.addAttribute("libri",this.bookService.getByIsbn(isbn));
		return "foundBooks.html";
	}
	@GetMapping("/admin/delete")
	public String deleteBook(Model model,@RequestParam Long id) {
		if(bookService.controllaPrestito(id)) {
		this.bookService.deleteById(id);
		model.addAttribute("libri",this.bookService.getAll());
		return "admin/libri.html";
		}
		return "admin/prestitoError.html";
	}

	@Transactional
	@GetMapping("/admin/authorsToAdd/{idAuthor}/{idBook}")
	public String addAuthorToBook(Model model,@PathVariable("idAuthor")Long idAuthor,@PathVariable("idBook")Long idBook) {
		model.addAttribute("book",this.bookService.addAuthor(idBook,idAuthor));
		return ("admin/Book.html");
	}
	@GetMapping("/books")
	public String books(Model model) {
		model.addAttribute("books",this.bookService.getAll());
		return "books.html";
	}

	@GetMapping("/book/{Id}")
	public String Book (@PathVariable("Id")Long id , Model model) {
		model.addAttribute("book",this.bookService.getById(id));
		return "book.html";
	}
}
