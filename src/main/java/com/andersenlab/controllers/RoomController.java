package com.andersenlab.controllers;



import com.andersenlab.dto.RoomDTO;
import com.andersenlab.dto.RoomPostPutDTO;
import com.andersenlab.services.RoomService;
import io.swagger.annotations.Api;
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
    public ResponseEntity<List<RoomDTO>> findAllRooms() {
        return ResponseEntity.ok().body(roomService.findAllRooms());
    }

    @GetMapping(value = "/{roomId}", produces = "application/json")
    @ApiOperation(value = "Get a room by id")
    public ResponseEntity<RoomDTO> findRoomById(@PathVariable("roomId") Long roomId)
    {
        RoomDTO roomDTO = roomService.findRoomById(roomId);
        return ResponseEntity.ok().body(roomDTO);
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    @ApiOperation(value = "Save a new room")
    /*@RequestBody говорит, что параметр будет именно в теле запроса
      @Valid - аннотация, которая активирует механизм валидации для данного бина*/
    public ResponseEntity<RoomPostPutDTO> saveRoom(
            @Valid @RequestBody RoomPostPutDTO roomPostPutDTO)
    {
        return ResponseEntity.status(201).body(roomService.saveRoom(roomPostPutDTO));
    }

    @DeleteMapping(value = "/{roomId}")
    @ApiOperation(value = "Delete room")
    public ResponseEntity<Long> deleteRoom(@PathVariable("roomId") Long roomId)
    {
        return ResponseEntity.ok().body(roomService.deleteRoom(roomId));
    }

    @PutMapping(produces = "application/json")
    @ApiOperation(value = "Update the room number")
    public ResponseEntity<RoomPostPutDTO> updateRoom(
            @RequestBody @Valid RoomPostPutDTO roomPostPutDTO) {
        return ResponseEntity.ok().body(roomService.updateRoom(roomPostPutDTO));
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
