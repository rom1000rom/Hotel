package com.andersenlab.dto;




import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"reservations"})
public class PersonDTO {

    private Long id;

    private String personName;

    private Boolean blacklisted = false;

    private Boolean admin = false;

    /*Аннотация позволяет избежать бесконечной рекурсии при отображении поля -
    коллекции(дочернего) в JSON*/
    @JsonBackReference
    private List<ReservationDTO> reservations;

    public PersonDTO(String personName) {
        this.personName = personName;
    }
}
