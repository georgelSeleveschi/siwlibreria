package it.uniroma3.siw.repository;
import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.model.Prestito;

import java.util.List;
import org.springframework.data.repository.CrudRepository;



public interface BookRepository extends CrudRepository<Book,Long> {
	
	public boolean existsBookByTitleAndIsbn(String title,String isbn);
	
	public List<Book> findByIsbn (String isbn);
	
	public List<Book> findByTitle (String title);
	
	public List<Book> findByPrestito(Prestito prestito);

	public List<Book> findAllByAutore(Author author);
}
