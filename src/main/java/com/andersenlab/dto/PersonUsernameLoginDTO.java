package com.andersenlab.dto;

import com.andersenlab.model.Reservation;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"reservations"})
public class PersonUsernameLoginDTO {

    private Long id;

    private String personName;

    private String encrytedPassword;

    private Boolean blacklisted = false;

    private Boolean admin = false;

    private List<Reservation> reservations;

    public PersonUsernameLoginDTO(String personName) {
        this.personName = personName;
    }
}
