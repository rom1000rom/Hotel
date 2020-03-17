package com.andersenlab.dto;


import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@EqualsAndHashCode(exclude = {"reservations"})
@Data
@NoArgsConstructor
public class RoomDTO {

    private Long id;

    @NotNull
    private String number;

    @JsonBackReference
    private HotelDto hotelId;

    @JsonManagedReference
    private List<ReservationDTO> reservations;

    public RoomDTO(String number) {
        this.number = number;
    }


}

