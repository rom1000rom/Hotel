package com.andersenlab.dto;

import java.util.Set;

import com.andersenlab.model.Facilities;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class HotelDto  {

	private Long id;

	private String hotelName;

	@JsonIgnore
	public Set<RoomDTO> roomSet;

	private Set<Facilities> serviceSet;

}
