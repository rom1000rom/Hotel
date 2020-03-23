package com.andersenlab.controllers;


import com.andersenlab.dto.PersonDto;
import com.andersenlab.dto.PersonRegistartionDto;
import com.andersenlab.services.PersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**Класс представляет собой REST-контроллёр, содержащий методы для
  обработки стандартных Http-запросов в отношении пользователей приложения.
@author Артемьев Р.А.
@version 11.03.2020 */
@RestController
//Swagger-аннотация, задаёт свойства API контроллёра
@Api(description = "Operations pertaining to persons in Hotel")
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping(produces = "application/json")
    //Swagger-аннотация, задаёт свойства API отдельного метода
    @ApiOperation(value = "Get a list of all persons")
    @ApiImplicitParam(name = "Authorization", paramType = "header",
            dataType = "string",required = true,
            defaultValue = "Token <paste_token_here>")
    public ResponseEntity<List<PersonDto>> findAllPersons() {
        return ResponseEntity.ok().body(personService.findAllPersons());
    }

    @GetMapping(value = "/{personId}", produces = "application/json")
    @ApiOperation(value = "Get a person by id")
    @ApiImplicitParam(name = "Authorization", paramType = "header",
            dataType = "string",required = true,
            defaultValue = "Token <paste_token_here>")
    public ResponseEntity<PersonDto> findPersonById(@PathVariable("personId") Long personId)
    {
        PersonDto personDTO = personService.findPersonById(personId);
        return ResponseEntity.ok().body(personDTO);
    }

    @PostMapping(produces = "application/json", consumes= "application/json")
    @ApiOperation(value = "Save a new person")
    @ApiImplicitParam(name = "Authorization", paramType = "header",
            dataType = "string",required = true,
            defaultValue = "Token <paste_token_here>")
    /*@RequestBody говорит, что параметр будет именно в теле запроса
      @Valid - аннотация, которая активирует механизм валидации для данного бина*/
    public ResponseEntity<PersonRegistartionDto> savePerson(
            @RequestBody @Valid PersonRegistartionDto personRegistartionDto)
    {
        //Кодируем пароль перед добавлением в базу
        personRegistartionDto.setEncrytedPassword(passwordEncoder.encode(
                personRegistartionDto.getEncrytedPassword()));
        personRegistartionDto.setId(personService.savePerson(personRegistartionDto));
        return ResponseEntity.status(201).body(personRegistartionDto);
    }

    @DeleteMapping(value = "/{personId}")
    @ApiOperation(value = "Delete person")
    @ApiImplicitParam(name = "Authorization", paramType = "header",
            dataType = "string",required = true,
            defaultValue = "Token <paste_token_here>")
    public ResponseEntity<Long> deletePerson(@PathVariable("personId") Long personId)
    {
        return ResponseEntity.ok().body(personService.deletePerson(personId));
    }

    @PutMapping(produces = "application/json")
    @ApiOperation(value = "Update the person name and password")
    @ApiImplicitParam(name = "Authorization", paramType = "header",
            dataType = "string",required = true,
            defaultValue = "Token <paste_token_here>")
    public ResponseEntity<PersonRegistartionDto> updatePerson(
            @RequestBody @Valid PersonRegistartionDto personRegistartionDto) {
        personRegistartionDto.setEncrytedPassword(passwordEncoder.encode(
                personRegistartionDto.getEncrytedPassword()));

        return ResponseEntity.ok().body(personService.updatePerson(personRegistartionDto));
    }

    @PutMapping(value = "addToBlacklist/{personId}", produces = "application/json")
    @ApiOperation(value = "Add user to the black list")
    @ApiImplicitParam(name = "Authorization", paramType = "header",
            dataType = "string",required = true,
            defaultValue = "Token <paste_token_here>")
    public ResponseEntity<Long> addToBlacklist(@PathVariable("personId") Long personId) {
        return ResponseEntity.ok().body(personService.addToBlacklist(personId));
    }

    @PutMapping(value = "removeFromBlacklist/{personId}", produces = "application/json")
    @ApiOperation(value = "Remove user from the black list")
    @ApiImplicitParam(name = "Authorization", paramType = "header",
            dataType = "string",required = true,
            defaultValue = "Token <paste_token_here>")
    public ResponseEntity<Long> removeFromBlacklist(@PathVariable("personId") Long personId) {
        return ResponseEntity.ok().body(personService.removeFromBlacklist(personId));
    }
}
