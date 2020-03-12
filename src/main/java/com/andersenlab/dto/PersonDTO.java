package com.andersenlab.dto;


import com.andersenlab.model.Reservation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"reservations"})
public class PersonDTO {

    private Long id;

    private String personName;

    @JsonIgnore
    private String encrytedPassword;

    private Boolean blacklisted = false;

    private Boolean admin = false;

    private List<Reservation> reservations;

    public PersonDTO(String personName, String encrytedPassword) {
        this.personName = personName;
        this.encrytedPassword = encrytedPassword;
    }
}
