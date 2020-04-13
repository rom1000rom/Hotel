package com.andersenlab.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import javax.persistence.*;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "person")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = { "version", "reservations", "invoices" })
@ToString(exclude = { "reservations", "invoices" })
public class Person {

	@Id
	@SequenceGenerator( name = "personSeq", sequenceName = "person_seq", allocationSize = 1, initialValue = 3 )
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "personSeq")
	private Long id;

	@Version
	@Column(name = "version")
	private Integer version;

	@Column(name = "name")
	private String name;

	@Column(name = "surname")
	private String surname;

	@Column(name = "password")
	private String password;

	@Column(name = "date_of_birth")
	private LocalDate dateOfBirth;

	/** Находится ли пользователь в "черном" списке */
	@Column(name = "blacklisted")
	private Boolean blacklisted = false;

	/** Является ли пользователь администратором */
	@Column(name = "admin")
	private Boolean admin = false;

	@Column(name = "country")
	private String country;

	@Column(name = "city")
	private String city;

	@Column(name = "street")
	private String street;

	@Column(name = "house")
	private String house;

	@Column(name = "apartment")
	private Integer apartment;

	@Column(name = "path_to_photo")
	private String pathToPhoto;

	@Fetch(FetchMode.JOIN)
	@OneToMany(mappedBy = "person")
	private List<Reservation> reservations;

	@Fetch(FetchMode.JOIN)
	@OneToMany(mappedBy = "personId")
	private Set<Invoice> invoices;

	public Person(String personName) {
		this.name = personName;
	}

}
