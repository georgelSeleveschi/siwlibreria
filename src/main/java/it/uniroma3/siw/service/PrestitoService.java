package it.uniroma3.siw.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.model.Prestito;
import it.uniroma3.siw.model.Student;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.PrestitoRepository;

@Service
public class PrestitoService {

	@Autowired
	private PrestitoRepository prestitoRepository;
	@Autowired
	private StudentService studentService;
	@Autowired
	private BookService bookService;

	@Transactional
	public void save(Prestito prestito) {
		prestitoRepository.save(prestito);
		
	}

	@Transactional
	public List<Prestito> getPrestitiAttivi(Student student) {
		List<Prestito> prestitiAttivi= new ArrayList<>();
		 List<Prestito> iterable = student.getPrestiti();
	      for(Prestito prestito : iterable) {
	    	 if(prestito.getDataFine().compareTo(LocalDate.now())>0)
	    		  prestitiAttivi.add(prestito);
	      }
		
		return prestitiAttivi;
	}

	@Transactional
	public boolean existPrestitiAttivi(Student student) {
		List<Prestito> iterable = (List<Prestito>) this.prestitoRepository.findAll();
		for(Prestito prestito : iterable) {
			// se ha prestito concluso cancello 
			if((prestito.getStudent().equals(student))&&(prestito.getDataFine().compareTo(LocalDate.now())>0)) {
				return true;
			}
			else
				if(prestito.getStudent().equals(student)&&(prestito.getDataFine().compareTo(LocalDate.now())<0))
					this.prestitoRepository.delete(prestito);
		}

		return false;
	}
	
	@Transactional
	public List<Prestito> getPrestitiConclusi(Student student) {
		List<Prestito> prestitiConclusi= new ArrayList<>();
		 List<Prestito> iterable = student.getPrestiti();
	      for(Prestito prestito : iterable) {
	    	 if(prestito.getDataFine().compareTo(LocalDate.now())<0)
	    		  prestitiConclusi.add(prestito);
	      }
		
		return prestitiConclusi;
	}

	@Transactional
	public boolean controlloPrestito(Prestito prestito, User user) {
		return (prestito.getDataFine().compareTo(prestito.getDataInizio())>0 && user.getMatricolaStudente()!=null);
	}

	@Transactional
	public void addPrestito(Prestito prestito, User user, Book book) {
		Student student= studentService.getById(user.getMatricolaStudente());
		List<Prestito> prestiti = student.getPrestiti();
		prestiti.add(prestito);
		student.setPrestiti(prestiti);

		prestito.setBook(book);
		prestito.setStudent(student);
		book.setPrestito(prestito);
		this.save(prestito); 
		this.bookService.saveBook(book);
	}

	@Transactional
	public List<Prestito> getAllPrestitiAttivi() {
		List<Prestito> prestitiAttivi= new ArrayList<>();
		 List<Prestito> iterable = (List<Prestito>) this.prestitoRepository.findAll();
	      for(Prestito prestito : iterable) {
	    	 if(prestito.getDataFine().compareTo(LocalDate.now())>0)
	    		  prestitiAttivi.add(prestito);
	      }
		
		return prestitiAttivi;
	}

	@Transactional
	public List<Prestito> getAllPrestitiConclusi() {
		List<Prestito> prestitiAttivi= new ArrayList<>();
		 List<Prestito> iterable = (List<Prestito>) this.prestitoRepository.findAll();
	      for(Prestito prestito : iterable) {
	    	 if(prestito.getDataFine().compareTo(LocalDate.now())<0)
	    		  prestitiAttivi.add(prestito);
	      }
		
		return prestitiAttivi;
	}

	public void deleteById(Long id) {
		this.prestitoRepository.deleteById(id);
		
	}
	
}
