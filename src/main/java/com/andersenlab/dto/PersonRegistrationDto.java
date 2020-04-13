package com.andersenlab.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonRegistrationDto {

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    private String password;

    @NotNull
    private LocalDate dateOfBirth;

    @NotNull
    private String country;

    @NotNull
    private String city;

    @NotNull
    private String street;

    @NotNull
    private String house;

    @NotNull
    private Integer apartment;

    private String pathToPhoto;

    public PersonRegistrationDto(String personName) {
        this.name = personName;
    }
}
