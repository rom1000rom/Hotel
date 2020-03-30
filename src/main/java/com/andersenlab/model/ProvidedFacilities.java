package com.andersenlab.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "provided_facilities")
@Data
@EqualsAndHashCode(exclude = { "version" })
public class ProvidedFacilities {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
