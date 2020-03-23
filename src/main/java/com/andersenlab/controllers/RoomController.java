package com.andersenlab.controllers;



import com.andersenlab.dto.RoomDto;

import com.andersenlab.services.RoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**Класс представляет собой REST-контроллёр, содержащий методы для
  обработки стандартных Http-запросов в отношении номеров отеля.
@author Артемьев Р.А.
@version 13.03.2020 */
@RestController
//Swagger-аннотация, задаёт свойства API контроллёра
@Api(description = "Operations pertaining to hotel rooms")
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping(produces = "application/json")
    //Swagger-аннотация, задаёт свойства API отдельного метода
    @ApiOperation(value = "Get a list of all rooms")
    @ApiImplicitParam(name = "Authorization", paramType = "header",
            dataType = "string",required = true,
            defaultValue = "Token <paste_token_here>")
    public ResponseEntity<List<RoomDto>> findAllRooms() {
        return ResponseEntity.ok().body(roomService.findAllRooms());
    }

    @GetMapping(value = "/{roomId}", produces = "application/json")
    @ApiOperation(value = "Get a room by id")
    @ApiImplicitParam(name = "Authorization", paramType = "header",
            dataType = "string",required = true,
            defaultValue = "Token <paste_token_here>")
    public ResponseEntity<RoomDto> findRoomById(@PathVariable("roomId") Long roomId)
    {
        RoomDto roomDTO = roomService.findRoomById(roomId);
        return ResponseEntity.ok().body(roomDTO);
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    @ApiOperation(value = "Save a new room")
    @ApiImplicitParam(name = "Authorization", paramType = "header",
            dataType = "string",required = true,
            defaultValue = "Token <paste_token_here>")
    /*@RequestBody говорит, что параметр будет именно в теле запроса
      @Valid - аннотация, которая активирует механизм валидации для данного бина*/
    public ResponseEntity<RoomDto> saveRoom(
             @RequestBody RoomDto roomDTO)
    {
        roomDTO.setId(roomService.saveRoom(roomDTO));
        return ResponseEntity.status(201).body(roomDTO);
    }

    @DeleteMapping(value = "/{roomId}")
    @ApiOperation(value = "Delete room")
    @ApiImplicitParam(name = "Authorization", paramType = "header",
            dataType = "string",required = true,
            defaultValue = "Token <paste_token_here>")
    public ResponseEntity<Long> deleteRoom(@PathVariable("roomId") Long roomId)
    {
        return ResponseEntity.ok().body(roomService.deleteRoom(roomId));
    }

    @PutMapping(produces = "application/json")
    @ApiOperation(value = "Update the room number")
    @ApiImplicitParam(name = "Authorization", paramType = "header",
            dataType = "string",required = true,
            defaultValue = "Token <paste_token_here>")
    public ResponseEntity<RoomDto> updateRoom(
            @RequestBody  RoomDto roomDTO) {
        return ResponseEntity.ok().body(roomService.updateRoom(roomDTO));
    }

}
