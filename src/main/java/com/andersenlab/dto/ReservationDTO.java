package com.andersenlab.dto;







import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonIgnore
    @JsonManagedReference
    @NotNull
    private PersonDTO person;

    @JsonIgnore
    @JsonManagedReference
    @NotNull
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
