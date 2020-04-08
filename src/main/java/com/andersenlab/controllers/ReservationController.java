package com.andersenlab.controllers;



import com.andersenlab.dto.ReservationDto;
import com.andersenlab.dto.page.ReservationPageDto;
import com.andersenlab.service.ReservationService;
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
import org.springframework.data.web.PageableDefault;
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

    @Autowired
    private MapperFacade mapperFacade;

    private static final Logger log = LogManager.getLogger(ReservationController.class);

    @GetMapping
    @ApiOperation(value = "Get a page of all reservations", authorizations = { @Authorization(value="apiKey") })
    public ResponseEntity<ReservationPageDto> findAllReservations(
            @RequestParam(name = "pageNumber") Integer pageNumber,
            @RequestParam(name = "pageSize") Integer pageSize) {
        log.debug("findAllReservations() - start");
        Pageable pageable = PageRequest.of(pageNumber, pageSize,
                Sort.Direction.ASC, "id");
        Page<ReservationDto> reservationPage = reservationService.findAllReservations(pageable);
        ReservationPageDto result = new ReservationPageDto();
        mapperFacade.map(reservationPage, result);
        log.debug("findAllReservations() - end: result = {}", result);

        return ResponseEntity.ok().body(result);
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

    @GetMapping(value = "findByRoomId/{roomId}")
    @ApiOperation(value = "Get a page reservations by Room id",
            authorizations = { @Authorization(value="apiKey") })
    public ResponseEntity<ReservationPageDto> findReservationsByRoomId(
            @PathVariable("roomId") Long roomId,
            @RequestParam(name = "pageNumber") Integer pageNumber,
            @RequestParam(name = "pageSize") Integer pageSize) {
        log.debug("findReservationsByRoomId() - start: roomId = {}", roomId);
        Pageable pageable = PageRequest.of(pageNumber, pageSize,
                Sort.Direction.ASC, "id");
        Page<ReservationDto> reservationPage = reservationService.findReservationsByRoomId(
                roomId, pageable);
        ReservationPageDto result = new ReservationPageDto();
        mapperFacade.map(reservationPage, result);
        log.debug("findReservationsByRoomId() - end: roomId = {}, result = {}",
                roomId, result);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "findByPersonId/{personId}")
    @ApiOperation(value = "Get a page reservations by Person id",
            authorizations = { @Authorization(value="apiKey") })
    public ResponseEntity<ReservationPageDto> findReservationsByPersonId(
            @PathVariable("personId") Long personId,
            @RequestParam(name = "pageNumber") Integer pageNumber,
            @RequestParam(name = "pageSize") Integer pageSize) {
        log.debug("findReservationsByPersonId() - start: personId = {}", personId);
        Pageable pageable = PageRequest.of(pageNumber, pageSize,
                Sort.Direction.ASC, "id");
        Page<ReservationDto> reservationPage = reservationService.findReservationsByPersonId(
                personId, pageable);
        ReservationPageDto result = new ReservationPageDto();
        mapperFacade.map(reservationPage, result);
        log.debug("findReservationsByPersonId() - end: personId = {}, result = {}",
                personId, result);

        return ResponseEntity.ok().body(result);
    }

}
