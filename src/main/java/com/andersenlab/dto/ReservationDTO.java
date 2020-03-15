package com.andersenlab.dto;






import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ReservationDTO {

    private Long id;

    /*Аннотация позволяет избежать бесконечной рекурсии при отображении объектного
    поля(родительского) в JSON*/
    @JsonManagedReference
    private PersonDTO person;

    @JsonManagedReference
    private RoomDTO room;

    private LocalDate dateBegin;

    private LocalDate dateEnd;

    public ReservationDTO(LocalDate dateBegin, LocalDate dateEnd) {
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
    }

}
