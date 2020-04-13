package com.andersenlab.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "provided_facilities")
@Data
@EqualsAndHashCode(exclude = { "version" })
public class ProvidedFacilities {

	@Id
	@SequenceGenerator( name = "providedFacilitiesSeq", sequenceName = "provided_facilities_seq",
			allocationSize = 1, initialValue = 9 )
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "providedFacilitiesSeq")
	private Long id;

	@Version
	private Integer version;

	@Column(name = "cost_facilities")
	private BigDecimal costFacilities;

	@Column(name = "count_facilities")
	private Long countFacilities;

	@Column(name = "date_facilities")
	private LocalDate dateFacilities;

	@OneToOne
	@JoinColumn(name = "facilities_id")
	private Facilities facilitiesId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invoice_id")
	private Invoice invoiceId;
}
