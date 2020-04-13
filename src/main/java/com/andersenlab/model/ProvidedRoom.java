package com.andersenlab.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "provided_room")
@Data
@EqualsAndHashCode(exclude = { "version" })
public class ProvidedRoom {

	@Id
	@SequenceGenerator( name = "providedRoomSeq", sequenceName = "provided_room_seq",
			allocationSize = 1, initialValue = 7 )
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "providedRoomSeq")
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
