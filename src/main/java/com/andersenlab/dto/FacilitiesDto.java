package com.andersenlab.dto;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FacilitiesDto {
  private Long id;
  @NotNull
  private String facilitiesNumber;
  @NotNull
  private String facilitiesName;
  private BigDecimal facilitiesPrice;

  @JsonBackReference(value = "hotel - facilities")
  private HotelDto hotelId;

}
