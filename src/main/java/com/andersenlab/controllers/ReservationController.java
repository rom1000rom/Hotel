package com.andersenlab.controllers;



import com.andersenlab.dto.ReservationDto;
import com.andersenlab.service.ReservationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
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
@RequestMapping(value = "/reservations", produces = "application/json")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    @ApiOperation(value = "Get a list of all reservations", authorizations = { @Authorization(value="apiKey") })
    public ResponseEntity<List<ReservationDto>> findAllReservations() {
        return ResponseEntity.ok().body(reservationService.findAllReservations());
    }

    @GetMapping(value = "/{reservationId}")
    @ApiOperation(value = "Get a reservation by id", authorizations = { @Authorization(value="apiKey") })
    public ResponseEntity<ReservationDto> findReservationById(
            @PathVariable("reservationId") Long reservationId)
    {
        return ResponseEntity.ok().body(reservationService.findReservationById(reservationId));
    }

    @PostMapping
    @ApiOperation(value = "Save a new reservation", authorizations = { @Authorization(value="apiKey") })
    public ResponseEntity<ReservationDto> saveReservation(
            @RequestBody  ReservationDto reservationDTO) {
        reservationDTO.setId(reservationService.saveReservation(reservationDTO));
        return ResponseEntity.ok().body(reservationDTO);
    }

    @PutMapping
    @ApiOperation(value = "Update the reservation DateBegin, DateEnd and Room",
            authorizations = { @Authorization(value="apiKey") })
    public ResponseEntity<ReservationDto> updateReservation(
            @RequestBody  ReservationDto reservationDto) {
        return ResponseEntity.ok().body(
                reservationService.updateReservation(reservationDto));
    }

    @DeleteMapping(value = "/{reservationId}")
    @ApiOperation(value = "Delete reservation", authorizations = { @Authorization(value="apiKey") })
    public ResponseEntity<Long> deleteReservation(
            @PathVariable("reservationId") Long reservationId)
    {
        return ResponseEntity.ok().body(reservationService.deleteReservation(reservationId));
    }

    @GetMapping(value = "findByPersonId/{personId}")
    @ApiOperation(value = "Get a list  reservations by Person id",
            authorizations = { @Authorization(value="apiKey") })
    public ResponseEntity<List<ReservationDto>> findReservationsByPersonId(
            @PathVariable("personId") Long personId) {
        return ResponseEntity.ok().body(reservationService.findReservationsByPersonId(personId));
    }

    @GetMapping(value = "findByRoomId/{roomId}")
    @ApiOperation(value = "Get a list  reservations by Room id",
            authorizations = { @Authorization(value="apiKey") })
    public ResponseEntity<List<ReservationDto>> findReservationsByRoomId(
            @PathVariable("roomId") Long roomId) {
        return ResponseEntity.ok().body(reservationService.findReservationsByRoomId(roomId));
    }

}
