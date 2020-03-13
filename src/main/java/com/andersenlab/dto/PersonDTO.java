package com.andersenlab.dto;


import com.andersenlab.model.Reservation;
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

    private List<Reservation> reservations;

    public PersonDTO(String personName) {
        this.personName = personName;
    }
}
