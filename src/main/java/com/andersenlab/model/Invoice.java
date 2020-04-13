package com.andersenlab.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "invoice")
@Data
@EqualsAndHashCode(exclude = { "version", "facilities", "rooms" })
public class Invoice {

	@Id
	@SequenceGenerator( name = "invoiceSeq", sequenceName = "invoice_seq",
			allocationSize = 1, initialValue = 3 )
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "invoiceSeq")
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
