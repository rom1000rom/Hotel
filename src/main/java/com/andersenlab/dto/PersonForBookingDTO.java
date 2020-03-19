package com.andersenlab.dto;



import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
public class PersonForBookingDTO {

    private Long id;

    @NotNull
    private String personName;

    private Boolean blacklisted = false;

    public PersonForBookingDTO(String personName) {
        this.personName = personName;
    }
}
