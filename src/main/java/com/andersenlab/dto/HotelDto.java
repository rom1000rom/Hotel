package com.andersenlab.dto;

import java.util.Set;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(exclude = {"id", "roomSet", "facilitiesSet"})
@NoArgsConstructor
public class HotelDto {
  private Long id;
  private String hotelName;

  @JsonManagedReference(value = "hotel - room")
  private Set<RoomDto> roomSet;

  @JsonManagedReference(value = "hotel - facilities")
  private Set<FacilitiesDto> facilitiesSet;

}
