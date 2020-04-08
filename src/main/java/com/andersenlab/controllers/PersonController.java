package com.andersenlab.controllers;


import com.andersenlab.dto.PersonDto;
import com.andersenlab.dto.PersonRegistartionDto;
import com.andersenlab.dto.page.PersonPageDto;
import com.andersenlab.security.JwtTokenUtil;
import com.andersenlab.service.PersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import ma.glasnost.orika.MapperFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;



/**Класс представляет собой REST-контроллёр, содержащий методы для
  обработки стандартных Http-запросов в отношении пользователей приложения.
@author Артемьев Р.А.
@version 11.03.2020 */
@RestController
//Swagger-аннотация, задаёт свойства API контроллёра
@Api(description = "Operations pertaining to persons in Hotel")
@RequestMapping(value = "/persons", produces = "application/json")
public class PersonController {

    @Autowired
    private PersonService personService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MapperFacade mapperFacade;

    private static final Logger log = LogManager.getLogger(PersonController.class);


    @GetMapping
    @ApiOperation(value = "Get a page of all persons", authorizations = { @Authorization(value="apiKey") })
    public ResponseEntity<PersonPageDto> findAllPersons(
            @RequestParam(name = "pageNumber") Integer pageNumber,
            @RequestParam(name = "pageSize") Integer pageSize) {
        log.debug("findAllPersons - start");
        Pageable pageable = PageRequest.of(pageNumber, pageSize,
                Sort.Direction.ASC, "id");
        Page<PersonDto> personPage = personService.findAllPersons(pageable);
        PersonPageDto result = new PersonPageDto();
        mapperFacade.map(personPage, result);
        log.debug("findAllPersons() - end: result = {}", result);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/{personId}")
    @ApiOperation(value = "Get a person by id", authorizations = { @Authorization(value="apiKey") })
    public ResponseEntity<PersonDto> findPersonById(@PathVariable("personId") Long personId)
    {

        PersonDto personDTO = personService.findPersonById(personId);
        return ResponseEntity.ok().body(personDTO);
    }

    @PostMapping(value = "logout")
    @ApiOperation(value = "Lets log out", authorizations = { @Authorization(value="apiKey") })
    /*@RequestBody говорит, что параметр будет именно в теле запроса
      @Valid - аннотация, которая активирует механизм валидации для данного бина*/
    public ResponseEntity<String> logoutPerson()
    {
        jwtTokenUtil.changeSecret();
        return ResponseEntity.status(201).body("You are logged out");
    }

    @PostMapping(value = "registration")
    @ApiOperation(value = "Save a new person")
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
    @ApiOperation(value = "Delete person", authorizations = { @Authorization(value="apiKey") })
    public ResponseEntity<Long> deletePerson(@PathVariable("personId") Long personId)
    {
        return ResponseEntity.ok().body(personService.deletePerson(personId));
    }

    @PutMapping
    @ApiOperation(value = "Update the person name and password",
            authorizations = { @Authorization(value="apiKey") })
    public ResponseEntity<PersonRegistartionDto> updatePerson(
            @RequestBody @Valid PersonRegistartionDto personRegistartionDto) {
        personRegistartionDto.setEncrytedPassword(passwordEncoder.encode(
                personRegistartionDto.getEncrytedPassword()));

        return ResponseEntity.ok().body(personService.updatePerson(personRegistartionDto));
    }

    @PutMapping(value = "addToBlacklist/{personId}")
    @ApiOperation(value = "Add user to the black list", authorizations = { @Authorization(value="apiKey") })
    public ResponseEntity<Long> addToBlacklist(@PathVariable("personId") Long personId) {
        return ResponseEntity.ok().body(personService.addToBlacklist(personId));
    }

    @PutMapping(value = "removeFromBlacklist/{personId}")
    @ApiOperation(value = "Remove user from the black list",
            authorizations = { @Authorization(value="apiKey") })
    public ResponseEntity<Long> removeFromBlacklist(@PathVariable("personId") Long personId) {
        return ResponseEntity.ok().body(personService.removeFromBlacklist(personId));
    }


}
