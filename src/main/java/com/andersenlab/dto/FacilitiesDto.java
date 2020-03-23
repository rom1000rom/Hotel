package com.andersenlab.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class FacilitiesDto {
	private Long id;
	private String serviceNumber;
	private String serviceName;
	private BigDecimal servicePrice;

	@JsonBackReference(value = "hotel - facilities")
	@NotNull
	private HotelDto hotelId;
}
