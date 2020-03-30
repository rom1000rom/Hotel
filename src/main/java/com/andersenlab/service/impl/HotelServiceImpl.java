package com.andersenlab.service.impl;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.andersenlab.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.andersenlab.dao.FacilitiesRepository;
import com.andersenlab.dao.HotelRepository;
import com.andersenlab.dao.RoomRepository;
import com.andersenlab.dto.HotelDto;
import com.andersenlab.exceptions.HotelServiceException;
import com.andersenlab.model.Facilities;
import com.andersenlab.model.Hotel;
import com.andersenlab.model.Room;
import ma.glasnost.orika.MapperFacade;

@Service
public class HotelServiceImpl implements HotelService {

  private static final String EXCEPTION_HOTEL_NOT_FOUND = "Hotel is not found";
  private static final String EXCEPTION_HOTEL_ALREADY_EXISTS = "Hotel already exists";
  private HotelRepository hotelRepository;
  private RoomRepository roomRepository;
  private FacilitiesRepository facilitiesRepository;
  private MapperFacade mapperFacade;

  @Autowired
  public HotelServiceImpl(HotelRepository hotelRepository, RoomRepository roomRepository,
      FacilitiesRepository facilitiesRepository, MapperFacade mapperFacade) {
    this.hotelRepository = hotelRepository;
    this.roomRepository = roomRepository;
    this.facilitiesRepository = facilitiesRepository;
    this.mapperFacade = mapperFacade;
  }


  @Override
  @Transactional
  public List<HotelDto> findAllHotel() {

    List<Hotel> findAllHotel = hotelRepository.findAll();
    return mapperFacade.mapAsList(findAllHotel, HotelDto.class);

  }



  @Override
  @Transactional
  public HotelDto findHotelById(Long id) {
    Hotel findById = hotelRepository.findById(id)
        .orElseThrow(() -> new HotelServiceException(EXCEPTION_HOTEL_NOT_FOUND));
    return mapperFacade.map(findById, HotelDto.class);
  }

  @Override
  @Transactional
  public Long saveHotel(HotelDto hotelDto) {

    Hotel existedHotel = hotelRepository.findByHotelName(hotelDto.getHotelName());

    if (existedHotel != null) {
      throw new HotelServiceException(EXCEPTION_HOTEL_ALREADY_EXISTS);
    }
    Hotel mappedHotel = mapperFacade.map(hotelDto, Hotel.class);
    Hotel newHotel = hotelRepository.save(mappedHotel);

    // if (newHotel.getRoomSet() != null) {
    //
    // newHotel.getRoomSet().stream().forEach((entity) -> roomRepository.save(entity));
    // }
    //
    // if (newHotel.getFacilitiesSet() != null) {
    // newHotel.getFacilitiesSet().stream().forEach((entity) -> facilitiesRepository.save(entity));
    // }


    //hotelRepository.save(newHotel);

    return newHotel.getId();
  }

  @Override
  @Transactional
  public Long updateHotel(HotelDto hotelDto) {

    Hotel existedHotel = hotelRepository.findById(hotelDto.getId())
        .orElseThrow(() -> new HotelServiceException(EXCEPTION_HOTEL_NOT_FOUND));
    existedHotel.setHotelName(hotelDto.getHotelName());


    Set<Room> existedRoomSet = existedHotel.getRoomSet();
    if (existedRoomSet != null) {
      Set<Room> newRoomSet = new HashSet<>();


      hotelDto.getRoomSet().stream().map((entity) -> mapperFacade.map(entity, Room.class))
          .forEach((entity) -> newRoomSet.add(entity));

      for (Room room : existedRoomSet) {
        if (!newRoomSet.contains(room)) {
          room.setHotelId(null);
        }
      }

      for (Room room : newRoomSet) {
        if (!existedRoomSet.contains(room)) {
          room.setHotelId(existedHotel);
          existedRoomSet.add(room);
        }
      }
    }

    Set<Facilities> existedFacilitiesSet = existedHotel.getFacilitiesSet();
    if (existedFacilitiesSet != null) {

      Set<Facilities> newFacilitiesSet = new HashSet<>();

      hotelDto.getFacilitiesSet().stream()
          .map((entity) -> mapperFacade.map(entity, Facilities.class))
          .forEach((entity) -> newFacilitiesSet.add(entity));

      existedFacilitiesSet.stream().filter((entity) -> !newFacilitiesSet.contains(entity))
          .forEach((entity) -> entity.setHotelId(null));

      newFacilitiesSet.stream().filter((entity) -> !existedFacilitiesSet.contains(entity))
          .forEach((entity) -> {
            entity.setHotelId(existedHotel);
            existedFacilitiesSet.add(entity);
          });
    }

    hotelRepository.save(existedHotel);

    return existedHotel.getId();
  }

  @Override
  @Transactional
  public Long deleteHotel(Long id) {

    if (hotelRepository.findById(id).isPresent()) {
      hotelRepository.deleteById(id);
    } else {
      new HotelServiceException(EXCEPTION_HOTEL_NOT_FOUND);
    }

    return id;
  }
}
