package com.andersenlab.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

@Data
public class ProvidedFacilitiesDto {
  private Long id;
  private BigDecimal costFacilities;
  private Long countFacilities;
  private LocalDate dateFacilities;

  private FacilitiesDto facilitiesId;

  @JsonBackReference(value = "invoice-facilities")
  private InvoiceDto invoiceId;
}
