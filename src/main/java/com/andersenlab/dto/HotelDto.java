package com.andersenlab.dto;

import java.util.Set;

import com.andersenlab.model.Facilities;
import com.andersenlab.model.Room;

import lombok.Data;

@Data
public class HotelDto {
	private Long id;
	private String hotelName;
	private Set<Room> roomSet;
	private Set<Facilities> serviceSet;
}
