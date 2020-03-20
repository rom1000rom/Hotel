package com.andersenlab.dto;







import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor

public class ReservationDTO {

    private Long id;

    /*Аннотация позволяет избежать бесконечной рекурсии при отображении объектного
    поля(родительского) в JSON*/
    @JsonBackReference(value = "person - reservation")
    private PersonDTO person;

    @JsonBackReference(value = "room - reservation")

    private RoomDTO room;

    @NotNull
    private LocalDate dateBegin;

    @NotNull
    private LocalDate dateEnd;

    public ReservationDTO(LocalDate dateBegin, LocalDate dateEnd) {
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
    }

}
