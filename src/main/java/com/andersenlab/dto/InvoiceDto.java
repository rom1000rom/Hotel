package com.andersenlab.dto;

import java.time.LocalDate;
import java.util.List;
import com.andersenlab.model.ProvidedRoom;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = {"facilities", "rooms"})
public class InvoiceDto {
  private Long id;
  private String invoiceNumber;
  private LocalDate dateBegin;
  private LocalDate dateEnd;

  @JsonBackReference
  private PersonDto personId;

  @JsonManagedReference(value = "invoice-facilities")
  private List<ProvidedFacilitiesDto> facilities;

  @JsonManagedReference(value = "invoice-room")
  private List<ProvidedRoomDto> rooms;
}
