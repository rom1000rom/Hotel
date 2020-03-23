package com.andersenlab.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andersenlab.dto.HotelDto;
import com.andersenlab.services.HotelService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/api/hotel", produces = APPLICATION_JSON_VALUE)
public class HotelController {

  private HotelService hotelService;

  @Autowired
  public HotelController(HotelService hotelService) {
    this.hotelService = hotelService;
  }

  @ApiOperation(value = "Resource to get hotel list", nickname = "getHotelList", httpMethod = "GET")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = String.class),
      @ApiResponse(code = 400, message = "Not found"),
      @ApiResponse(code = 500, message = "Server failure")})
  @GetMapping(value = "/list")
  List<HotelDto> getHotelList() {
    return hotelService.findAllHotel();

  }

  @ApiOperation(value = "Resource to get hotel by id", nickname = "getHotelById",
      httpMethod = "GET")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = String.class),
      @ApiResponse(code = 400, message = "Not found"),
      @ApiResponse(code = 500, message = "Server failure")})
  @GetMapping(value = "/{id}")
  public HotelDto getHotelById(@PathVariable("id") Long id) {
    return hotelService.findHotelById(id);

  }

  @ApiOperation(value = "Resource to create hotel", nickname = "createHotel", httpMethod = "POST")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = String.class),
      @ApiResponse(code = 400, message = "Not found"),
      @ApiResponse(code = 500, message = "Server failure")})
  @PostMapping(value = "/create")
  Long createHotel(@RequestBody HotelDto hotelDto) {
    return hotelService.saveHotel(hotelDto);

  }

  @ApiOperation(value = "Resource to update hotel", nickname = "updateHotel", httpMethod = "PUT")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = String.class),
      @ApiResponse(code = 400, message = "Not found"),
      @ApiResponse(code = 500, message = "Server failure")})
  @PutMapping(value = "/update")
  void updateHotel(@RequestBody HotelDto hotelDto) {
    hotelService.updateHotel(hotelDto);
  }

  @ApiOperation(value = "Resource to delete hotel", nickname = "deleteHotel", httpMethod = "DELETE")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = String.class),
      @ApiResponse(code = 400, message = "Not found"),
      @ApiResponse(code = 500, message = "Server failure")})
  @DeleteMapping(value = "/{hotelId}")
  void deleteHotel(@PathVariable Long hotelId) {
    hotelService.deleteHotel(hotelId);
  }
}
