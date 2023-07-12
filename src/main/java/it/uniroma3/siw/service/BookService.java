package it.uniroma3.siw.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.controller.BookController;
import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.model.Prestito;
import it.uniroma3.siw.repository.BookRepository;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;
	@Autowired 
	private AuthorService authorService;
	@Autowired 
	private PrestitoService prestitoService;

	private String absolutepath=BookController.class.getProtectionDomain().getCodeSource().getLocation().getPath()+"/static/images";

	@Transactional
	public List<Book> getAll() {
		List<Book> result = new ArrayList<>();
		Iterable<Book> iterable = this.bookRepository.findAll();
		for(Book book : iterable)
			result.add(book);
		return result;
	}

	@Transactional
	public List<Book> getAllNonInPrestito() {
		List<Book> iterable = this.getAll();
		List<Prestito> prestiti=this.prestitoService.getAllPrestitiAttivi();
		for(Prestito prestito:prestiti) {
			iterable.remove(prestito.getBook());
		}
		return iterable;
	}

	//	@Transactional
	//    public List<User> getAllUsers() {
	//        List<User> result = new ArrayList<>();
	//        Iterable<User> iterable = this.userRepository.findAll();
	//        for(User user : iterable)
	//            result.add(user);
	//        return result;
	//    }


	@Transactional
	public Book getById(Long id) {
		return bookRepository.findById(id).get();
	}

	@Transactional
	public boolean existsBookByTitleAndIsbn(String title, String isbn) {
		return this.bookRepository.existsBookByTitleAndIsbn(title, isbn);
	}

	@Transactional
	public void newBook(MultipartFile file, Book book) throws IOException {
		File pathdest= new File(absolutepath,file.getOriginalFilename());
		pathdest.createNewFile();
		System.out.println(absolutepath);
		Files.copy(file.getInputStream(),pathdest.toPath(),StandardCopyOption.REPLACE_EXISTING);
		book.setUrlImage("/images/"+file.getOriginalFilename());
		this.bookRepository.save(book);

	}

	@Transactional
	public List<Book> getByTitle(String title) {

		return this.bookRepository.findByTitle(title);
	}

	@Transactional
	public List<Book> getByIsbn(String isbn) {

		return this.bookRepository.findByIsbn(isbn);
	}

	@Transactional
	public void deleteById(Long id) {
		this.getById(id).setAutore(null);
		this.bookRepository.deleteById(id);

	}

	@Transactional
	public Book addAuthor(Long idBook, Long idAuthor) {
		Book book=this.getById(idBook);
		Author author=this.authorService.getById(idAuthor);
		book.setAutore(author);
		author.getLibri().add(book);
		this.authorService.save(author);
		this.bookRepository.save(book);
		return book;
	}

	public boolean controllaPrestito(Long id) {
		Book book=this.getById(id);
		if(book.getPrestito()==null)
			return true;
		else
			if(book.getPrestito().getDataFine().compareTo(LocalDate.now())>0) {
				book.setPrestito(null);
				return true;
			}
			else
				return false;
	}

	public void cancellaAutoreDaiLibri(Author author) {
		List<Book> books=this.bookRepository.findAllByAutore(author);
		for(Book book : books) {
			book.setAutore(null);
		}
	}
	public void saveBook(Book book) {
		this.bookRepository.save(book);
	}
}



