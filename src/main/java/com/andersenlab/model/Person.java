package com.andersenlab.model;

import java.util.List;
import java.util.Set;
import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "person")
@Data
@EqualsAndHashCode(exclude = { "version", "reservations", "invoices" })
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Version
	private Integer version;

	private String encrytedPassword;

	/** Находится ли пользователь в "черном" списке */
	private Boolean blacklisted = false;

	/** Является ли пользователь администратором */
	private Boolean admin = false;

	@Column(name = "person_name")
	private String personName;

	@OneToMany(mappedBy = "person")
	private List<Reservation> reservations;

	@OneToMany(mappedBy = "personId")
	private Set<Invoice> invoices;

	public List<Reservation> getReservations() {
		return reservations;
	}

	public Person() {
	}

	public Person(String personName, String encrytedPassword) {
		this.encrytedPassword = encrytedPassword;
		this.personName = personName;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	public Boolean getBlacklisted() {
		return blacklisted;
	}

	public void setBlacklisted(Boolean blacklisted) {
		this.blacklisted = blacklisted;
	}

	public String getEncrytedPassword() {
		return encrytedPassword;
	}

	public void setEncrytedPassword(String encrytedPassword) {
		this.encrytedPassword = encrytedPassword;
	}
}
