package com.andersenlab.dto;


import lombok.Data;
import lombok.NoArgsConstructor;




@Data
@NoArgsConstructor
public class PersonUsernameLoginDTO {

    private Long id;

    private String personName;

    private String encrytedPassword;

    private Boolean blacklisted = false;

    private Boolean admin = false;

    public PersonUsernameLoginDTO(String personName) {
        this.personName = personName;
    }
}
