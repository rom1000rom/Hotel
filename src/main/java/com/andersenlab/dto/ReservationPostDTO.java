package com.andersenlab.dto;




import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor

public class ReservationPostDTO {

    private Long id;

    @NotNull
    private PersonForBookingDTO person;

    @NotNull
    private RoomPostPutDTO room;

    @NotNull
    private LocalDate dateBegin;

    @NotNull
    private LocalDate dateEnd;

    public ReservationPostDTO(LocalDate dateBegin, LocalDate dateEnd) {
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
    }

}
