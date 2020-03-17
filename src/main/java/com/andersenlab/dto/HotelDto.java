package com.andersenlab.dto;

import java.util.Set;

import com.andersenlab.model.Facilities;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

@Data
public class HotelDto  {

	private Long id;

	private String hotelName;

	@JsonManagedReference
	public Set<RoomDTO> roomSet;

	@JsonManagedReference
	private Set<Facilities> serviceSet;

}
