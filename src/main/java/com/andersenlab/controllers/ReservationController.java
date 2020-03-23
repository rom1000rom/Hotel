package com.andersenlab.controllers;



import com.andersenlab.dto.ReservationDto;
import com.andersenlab.services.ReservationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
    @ApiImplicitParam(name = "Authorization", paramType = "header",
            dataType = "string",required = true,
            defaultValue = "Token <paste_token_here>")
    public ResponseEntity<List<ReservationDto>> findAllReservations() {
        return ResponseEntity.ok().body(reservationService.findAllReservations());
    }

    @GetMapping(value = "/{reservationId}", produces = "application/json")
    @ApiOperation(value = "Get a reservation by id")
    @ApiImplicitParam(name = "Authorization", paramType = "header",
            dataType = "string",required = true,
            defaultValue = "Token <paste_token_here>")
    public ResponseEntity<ReservationDto> findReservationById(
            @PathVariable("reservationId") Long reservationId)
    {
        return ResponseEntity.ok().body(reservationService.findReservationById(reservationId));
    }

    @PostMapping(produces = "application/json", consumes= "application/json")
    @ApiOperation(value = "Save a new reservation")
    @ApiImplicitParam(name = "Authorization", paramType = "header",
            dataType = "string",required = true,
            defaultValue = "Token <paste_token_here>")
    public ResponseEntity<ReservationDto> saveReservation(
            @RequestBody  ReservationDto reservationDTO) {
        reservationDTO.setId(reservationService.saveReservation(reservationDTO));
        return ResponseEntity.status(201).body(reservationDTO);
    }

    @DeleteMapping(value = "/{reservationId}")
    @ApiOperation(value = "Delete reservation")
    @ApiImplicitParam(name = "Authorization", paramType = "header",
            dataType = "string",required = true,
            defaultValue = "Token <paste_token_here>")
    public ResponseEntity<Long> deleteReservation(
            @PathVariable("reservationId") Long reservationId)
    {
        return ResponseEntity.ok().body(reservationService.deleteReservation(reservationId));
    }

    @GetMapping(value = "findByPersonId/{personId}", produces = "application/json")
    @ApiOperation(value = "Get a list  reservations by Person id")
    @ApiImplicitParam(name = "Authorization", paramType = "header",
            dataType = "string",required = true,
            defaultValue = "Token <paste_token_here>")
    public ResponseEntity<List<ReservationDto>> findReservationsByPersonId(
            @PathVariable("personId") Long personId) {
        return ResponseEntity.ok().body(reservationService.findReservationsByPersonId(personId));
    }

    @GetMapping(value = "findByRoomId/{roomId}", produces = "application/json")
    @ApiOperation(value = "Get a list  reservations by Room id")
    @ApiImplicitParam(name = "Authorization", paramType = "header",
            dataType = "string",required = true,
            defaultValue = "Token <paste_token_here>")
    public ResponseEntity<List<ReservationDto>> findReservationsByRoomId(
            @PathVariable("roomId") Long roomId) {
        return ResponseEntity.ok().body(reservationService.findReservationsByRoomId(roomId));
    }

}
