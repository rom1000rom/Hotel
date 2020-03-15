package com.andersenlab.dto;

import java.util.Set;

import com.andersenlab.model.Facilities;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

@Data
public class HotelDto {

	private Long id;

	private String hotelName;

	@JsonBackReference
	private Set<RoomDTO> roomSet;

	private Set<Facilities> serviceSet;

}
