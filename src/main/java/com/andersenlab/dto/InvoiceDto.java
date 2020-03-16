package com.andersenlab.dto;

import java.time.LocalDate;
import java.util.List;
import com.andersenlab.model.Person;
import com.andersenlab.model.ProvidedFacilities;
import lombok.Data;

@Data
public class InvoiceDto {
	private Long id;
	private String invoiceNumber;
	private LocalDate dateBegin;
	private LocalDate dateEnd;
	private PersonDTO personId;
	private List<ProvidedFacilities> facilities;
}
