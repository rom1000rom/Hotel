package com.andersenlab.dto;

import java.util.Set;
import lombok.Data;

@Data
public class HotelDto {
	private Long id;
	private String hotelName;
	private Set<RoomDTO> roomSet;
	private Set<FacilitiesDto> serviceSet;
}
