package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Prestito;
import it.uniroma3.siw.model.Student;

public interface PrestitoRepository extends CrudRepository<Prestito, Long> {

	Iterable<Prestito> findByStudent(Student student);

}
