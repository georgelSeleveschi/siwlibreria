package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Student;
public interface StudentRepository extends CrudRepository<Student,Long>{
	
	public boolean existsStudentByMatricola(Long matricola);
}
