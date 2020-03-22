package com.andersenlab.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class FacilitiesDto {
  private Long id;
  private String facilitiesNumber;
  private String facilitiesName;
  private BigDecimal facilitiesPrice;

  @JsonBackReference(value = "hotel-facilities")
  private HotelDto hotelId;

}
