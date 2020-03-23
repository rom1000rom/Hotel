package com.andersenlab.dto;



import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;



@Data
@NoArgsConstructor
public class PersonRegistartionDto {

    private Long id;

    @NotNull
    private String personName;

    @NotNull
    private String encrytedPassword;

    private Boolean blacklisted = false;

    private Boolean admin = false;

    public PersonRegistartionDto(String personName) {
        this.personName = personName;
    }
}
