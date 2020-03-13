package com.andersenlab.model;

import java.util.List;
import java.util.Set;
import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "person")
@Data
@NoArgsConstructor
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

	@Fetch(FetchMode.JOIN)
	@OneToMany(mappedBy = "person")
	private List<Reservation> reservations;

	@Fetch(FetchMode.JOIN)
	@OneToMany(mappedBy = "personId")
	private Set<Invoice> invoices;

	public Person(String personName) {
		this.personName = personName;
	}

}
