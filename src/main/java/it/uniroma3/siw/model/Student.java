package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Student {
	@Id
	private Long matricola;
	private String nome;
	private String cognome;
	private String CF;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateOfBirth;
	@OneToMany(mappedBy="student")
	private List<Prestito> prestiti;
	
	public List<Prestito> getPrestiti() {
		return prestiti;
	}
	public void setPrestiti(List<Prestito> prestiti) {
		this.prestiti = prestiti;
	}
	public Long getMatricola() {
		return matricola;
	}
	public void setMatricola(Long matricola) {
		this.matricola = matricola;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getCF() {
		return CF;
	}
	public void setCF(String cF) {
		CF = cF;
	}
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	

}
