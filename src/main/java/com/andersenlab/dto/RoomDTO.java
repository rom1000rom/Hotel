package com.andersenlab.dto;


import com.andersenlab.model.Reservation;
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

    private Long hotelid;

    private List<Reservation> reservations;

    public RoomDTO(String number) {
        this.number = number;
    }
}

