package com.andersenlab.dto;




import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"reservations"})
public class PersonDto {

    private Long id;

    @NotNull
    private String personName;

    private Boolean blacklisted = false;

    private Boolean admin = false;

    /*Аннотация позволяет избежать бесконечной рекурсии при отображении поля -
    коллекции(дочернего) в JSON*/
    @JsonManagedReference(value = "person - reservation")
    private List<ReservationDto> reservations;

    public PersonDto(String personName) {
        this.personName = personName;
    }
}
