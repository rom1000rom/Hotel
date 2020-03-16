package com.andersenlab.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;

@Data
public class ProvidedRoomDto {
  private Long id;
  private BigDecimal costRoom;
  private LocalDate dateRoom;
  private RoomDTO roomId;
  private InvoiceDto invoiceId;
}
