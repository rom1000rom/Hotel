package com.andersenlab.dto;


import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@EqualsAndHashCode(exclude = {"reservations"})
@Data
@NoArgsConstructor
public class RoomDto {

    private Long id;

    @NotNull
    private String number;

    @NotNull
    private Integer maxGuests;

    @NotNull
    private Integer price;

    @JsonBackReference(value = "hotel - room")
    @NotNull
    private HotelDto hotelId;

    @JsonManagedReference (value = "room - reservation")
    private List<ReservationDto> reservations;

    public RoomDto(String number) {
        this.number = number;
    }


}

