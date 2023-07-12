package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Student;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.StudentRepository;


@Service
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private PrestitoService prestitoService;
	@Autowired
	private UserService userService;
	@Autowired
	private CredentialsService credentialsService;

	@Transactional
	public Student getById(Long matricola) {
		return studentRepository.findById(matricola).orElse(null);
	}

	@Transactional
	public boolean controlloStudent(Student student) {
		int annoDiNascita=student.getDateOfBirth().getYear();
		return (!this.studentRepository.existsById(student.getMatricola()))&&(annoDiNascita<2003 && annoDiNascita>1900);
	}

	@Transactional
	public void save(Student student) {
		this.studentRepository.save(student);

	}

	@Transactional
	public Iterable<Student> getAll() {
		return this.studentRepository.findAll();
	}

	@Transactional
	public void deleteById(Long id) {
		this.studentRepository.deleteById(id);

	}

	public boolean controlloPrestiti(Long id) {
		Student student=this.getById(id);
		//tono prestiti attivi e cancello quelli conclusi per fare in modo che si possa cancellare
		return prestitoService.existPrestitiAttivi(student);
	}

	public boolean controlloMatricola(Student student) {
		return this.studentRepository.existsStudentByMatricola(student.getMatricola());
	}

	public void cancelloUser(Long id) {
		User user=userService.getByStudent(this.getById(id));
		if(user!=null) {
			Credentials credentials=credentialsService.getByUser(user);
			this.credentialsService.deleteById(credentials.getId());
//			this.userService.deleteById(user.getId());
		}
	}

}
