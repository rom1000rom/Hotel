package com.andersenlab.dto;







import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor

public class ReservationDto {

    private Long id;

    /*Аннотация позволяет избежать бесконечной рекурсии при отображении объектного
    поля(родительского) в JSON*/
    @JsonBackReference(value = "person - reservation")
    @NotNull
    private PersonDto person;

    @JsonBackReference(value = "room - reservation")
    @NotNull
    private RoomDto room;

    @NotNull
    private LocalDate dateBegin;

    @NotNull
    private LocalDate dateEnd;

    public ReservationDto(LocalDate dateBegin, LocalDate dateEnd) {
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
    }

}
