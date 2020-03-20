package com.andersenlab.controllers;



import com.andersenlab.dto.ReservationDTO;
import com.andersenlab.services.ReservationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**Класс представляет собой REST-контроллёр, содержащий методы для
  обработки стандартных Http-запросов в отношении юронирований номеров отеля.
@author Артемьев Р.А.
@version 14.03.2020 */
@RestController
//Swagger-аннотация, задаёт свойства API контроллёра
@Api(description = "Operations pertaining to hotel room booking")
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping(produces = "application/json")
    @ApiOperation(value = "Get a list of all reservations")
    public ResponseEntity<List<ReservationDTO>> findAllReservations() {
        return ResponseEntity.ok().body(reservationService.findAllReservations());
    }

    @GetMapping(value = "/{reservationId}", produces = "application/json")
    @ApiOperation(value = "Get a reservation by id")
    public ResponseEntity<ReservationDTO> findReservationById(
            @PathVariable("reservationId") Long reservationId)
    {
        return ResponseEntity.ok().body(reservationService.findReservationById(reservationId));
    }

    @PostMapping(produces = "application/json", consumes= "application/json")
    @ApiOperation(value = "Save a new reservation")
    public ResponseEntity<ReservationDTO> saveReservation(
            @RequestBody @Valid ReservationDTO reservationDTO) {
        reservationDTO.setId(reservationService.saveReservation(reservationDTO));
        return ResponseEntity.status(201).body(reservationDTO);
    }

    @DeleteMapping(value = "/{reservationId}")
    @ApiOperation(value = "Delete reservation")
    public ResponseEntity<Long> deleteReservation(
            @PathVariable("reservationId") Long reservationId)
    {
        return ResponseEntity.ok().body(reservationService.deleteReservation(reservationId));
    }

    @GetMapping(value = "findByPersonId/{personId}", produces = "application/json")
    @ApiOperation(value = "Get a list  reservations by Person id")
    public ResponseEntity<List<ReservationDTO>> findReservationsByPersonId(
            @PathVariable("personId") Long personId) {
        return ResponseEntity.ok().body(reservationService.findReservationsByPersonId(personId));
    }

    @GetMapping(value = "findByRoomId/{roomId}", produces = "application/json")
    @ApiOperation(value = "Get a list  reservations by Room id")
    public ResponseEntity<List<ReservationDTO>> findReservationsByRoomId(
            @PathVariable("roomId") Long roomId) {
        return ResponseEntity.ok().body(reservationService.findReservationsByRoomId(roomId));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
