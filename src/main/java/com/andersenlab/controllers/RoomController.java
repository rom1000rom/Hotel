package com.andersenlab.controllers;



import com.andersenlab.dto.PersonDto;
import com.andersenlab.dto.RoomDto;

import com.andersenlab.dto.page.PersonPageDto;
import com.andersenlab.dto.page.RoomPageDto;
import com.andersenlab.service.RoomService;
import io.swagger.annotations.*;
import ma.glasnost.orika.MapperFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


/**Класс представляет собой REST-контроллёр, содержащий методы для
  обработки стандартных Http-запросов в отношении номеров отеля.
@author Артемьев Р.А.
@version 13.03.2020 */
@RestController
//Swagger-аннотация, задаёт свойства API контроллёра
@Api(description = "Operations pertaining to hotel rooms")
@RequestMapping(value = "/rooms", produces = "application/json")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private MapperFacade mapperFacade;

    private static final Logger log = LogManager.getLogger(RoomController.class);

    @GetMapping
    @ApiOperation(value = "Get a page of all rooms", authorizations = { @Authorization(value="apiKey") })
    public ResponseEntity<RoomPageDto> findAllRooms(
            @RequestParam(name = "pageNumber") Integer pageNumber,
            @RequestParam(name = "pageSize") Integer pageSize) {
        log.debug("findAllRooms - start");
        Pageable pageable = PageRequest.of(pageNumber, pageSize,
                Sort.Direction.ASC, "id");
        Page<RoomDto> roomPage = roomService.findAllRooms(pageable);
        RoomPageDto result = new RoomPageDto();
        mapperFacade.map(roomPage, result);
        log.debug("findAllRooms() - end: result = {}", result);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/{roomId}")
    @ApiOperation(value = "Get a room by id", authorizations = { @Authorization(value="apiKey") })
    public ResponseEntity<RoomDto> findRoomById(@PathVariable("roomId") Long roomId)
    {
        RoomDto roomDTO = roomService.findRoomById(roomId);
        return ResponseEntity.ok().body(roomDTO);
    }

    @PostMapping
    @ApiOperation(value = "Save a new room", authorizations = { @Authorization(value="apiKey") })
    /*@RequestBody говорит, что параметр будет именно в теле запроса
      @Valid - аннотация, которая активирует механизм валидации для данного бина*/
    public ResponseEntity<RoomDto> saveRoom(
             @RequestBody RoomDto roomDTO)
    {
        roomDTO.setId(roomService.saveRoom(roomDTO));
        return ResponseEntity.status(201).body(roomDTO);
    }

    @DeleteMapping(value = "/{roomId}")
    @ApiOperation(value = "Delete room", authorizations = { @Authorization(value="apiKey") })
    public ResponseEntity<Long> deleteRoom(@PathVariable("roomId") Long roomId)
    {
        return ResponseEntity.ok().body(roomService.deleteRoom(roomId));
    }

    @PutMapping
    @ApiOperation(value = "Update the room number", authorizations = { @Authorization(value="apiKey") })
    public ResponseEntity<RoomDto> updateRoom(
            @RequestBody  RoomDto roomDTO) {
        return ResponseEntity.ok().body(roomService.updateRoom(roomDTO));
    }

}
