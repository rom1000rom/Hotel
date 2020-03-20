package com.andersenlab.dto;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class RoomPostPutDTO {

    private Long id;

    @NotNull
    private String number;

    @JsonBackReference
    private HotelPostPutDto hotelId;

    public RoomPostPutDTO(String number) {
        this.number = number;
    }

}

