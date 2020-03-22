package com.andersenlab.dto;

import java.util.Set;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = {"id", "roomSet", "serviceSet"})
public class HotelDto {
  private Long id;
  private String hotelName;

  @JsonManagedReference(value = "hotel - room")
  private Set<RoomDto> roomSet;

  @JsonManagedReference(value = "hotel-facilities")
  private Set<FacilitiesDto> serviceSet;

}
