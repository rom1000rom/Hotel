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
@Table(name = "provided_room")
@Data
@EqualsAndHashCode(exclude = { "version" })
public class ProvidedRoom {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Version
	private Integer version;

	@Column(name = "cost_room")
	private BigDecimal costRoom;

	@Column(name = "date_room")
	private LocalDate dateRoom;

	@OneToOne
	@JoinColumn(name = "room_id")
	private Room roomId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invoice_id")
	private Invoice invoiceId;
}
