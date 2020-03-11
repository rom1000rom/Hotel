package com.andersenlab.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class FacilitiesDto {
	private Long id;
	private String serviceNumber;
	private String serviceName;
	private BigDecimal servicePrice;
	private HotelDto hotelId;
}
