package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.repository.AuthorRepository;

@Service
public class AuthorService {

	@Autowired 
	AuthorRepository authorRepository;

	@Transactional
	public void save(Author author) {
		this.authorRepository.save(author);
	}

	@Transactional
	public Author getById(Long idAuthor) {
		return this.authorRepository.findById(idAuthor).get();
	}

	@Transactional
	private boolean existsAuthorByNomeAndCognome(String nome, String cognome) {
		return this.authorRepository.existsAuthorByNomeAndCognome(nome, cognome);
	}

	@Transactional
	public Iterable<Author> getAll() {
		return this.authorRepository.findAll();
	}

	@Transactional
	public boolean controlloAuthor(Author author) {
		return (!this.existsAuthorByNomeAndCognome(author.getNome(), author.getCognome()))&&(author.getDateOfBirth().getYear()<2005);
	}

	public boolean controlloNomeCognome(Author author) {
		return this.existsAuthorByNomeAndCognome(author.getNome(), author.getCognome());
	}

	public void deleteById(Long id) {
		this.authorRepository.deleteById(id);
		
	}

}
