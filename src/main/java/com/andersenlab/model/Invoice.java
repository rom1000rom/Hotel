package com.andersenlab.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "invoice")
@Data
@EqualsAndHashCode(exclude = { "version", "facilities", "rooms" })
public class Invoice {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Version
	private Integer version;

	@Column(name = "invoice_number")
	private String invoiceNumber;

	@Column(name = "date_begin")
	private LocalDate dateBegin;

	@Column(name = "date_end")
	private LocalDate dateEnd;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "person_id")
	private Person personId;

	@OneToMany(mappedBy = "invoiceId")
	private List<ProvidedFacilities> facilities;

	@OneToMany(mappedBy = "invoiceId")
	private List<ProvidedRoom> rooms;
}
