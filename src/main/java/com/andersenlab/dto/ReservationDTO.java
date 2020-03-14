package com.andersenlab.dto;



import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ReservationDTO {

    private Long id;

    private Long personId;

    private Long roomId;

    private LocalDate dateBegin;

    private LocalDate dateEnd;

    public ReservationDTO(LocalDate dateBegin, LocalDate dateEnd) {
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
    }

}
