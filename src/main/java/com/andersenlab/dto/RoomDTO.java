package com.andersenlab.dto;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(exclude = {"reservations"})
@Data
@NoArgsConstructor
public class RoomDTO {

    private Long id;

    private String number;

    @JsonManagedReference
    private HotelDto hotelId;

    @JsonBackReference
    private List<ReservationDTO> reservations;

    public RoomDTO(String number) {
        this.number = number;
    }
}

