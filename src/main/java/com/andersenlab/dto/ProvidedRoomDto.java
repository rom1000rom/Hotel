package com.andersenlab.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

@Data
public class ProvidedRoomDto {
  private Long id;
  private BigDecimal costRoom;
  private LocalDate dateRoom;

  private RoomDto roomId;

  @JsonBackReference(value = "invoice-room")
  private InvoiceDto invoiceId;
}
