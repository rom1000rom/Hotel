package com.andersenlab.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

@Data
public class HotelDto  {

	private Long id;

	private String hotelName;

	@JsonManagedReference(value = "hotel - room")
	public Set<RoomDTO> roomSet;

	@JsonManagedReference(value = "hotel - facilities")
	private Set<FacilitiesDto> serviceSet;

}
