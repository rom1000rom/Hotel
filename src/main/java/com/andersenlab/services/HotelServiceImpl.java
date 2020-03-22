package com.andersenlab.services;

import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.andersenlab.dao.HotelRepository;
import com.andersenlab.dao.RoomRepository;
import com.andersenlab.dto.HotelDto;
import com.andersenlab.exceptions.HotelServiceException;
import com.andersenlab.model.Facilities;
import com.andersenlab.model.Hotel;
import com.andersenlab.model.Room;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;

@Service
public class HotelServiceImpl implements HotelService {

  private static final String EXCEPTION_MESSAGE = "Hotel is not found";
  private HotelRepository hotelRepository;
  @Autowired
  private RoomRepository roomRepository;
  private MapperFacade mapperFacade;

  @Autowired
  public HotelServiceImpl(HotelRepository hotelRepository, MapperFacade mapperFacade) {
    this.hotelRepository = hotelRepository;
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
        .orElseThrow(() -> new HotelServiceException(EXCEPTION_MESSAGE));
    return mapperFacade.map(findById, HotelDto.class);
  }

  @Override
  @Transactional
  public Long saveHotel(HotelDto hotelDto) {

    Hotel newHotel = hotelRepository.save(mapperFacade.map(hotelDto, Hotel.class));
    newHotel.getRoomSet().stream().forEach((entity) -> roomRepository.save(entity));
    return newHotel.getId();
  }

  @Override
  @Transactional
  public Long updateHotel(HotelDto hotelDto) {

    Hotel existedHotel = hotelRepository.findById(hotelDto.getId())
        .orElseThrow(() -> new HotelServiceException(EXCEPTION_MESSAGE));
    existedHotel.setHotelName(hotelDto.getHotelName());

    Set<Room> roomSet = existedHotel.getRoomSet();
    roomSet.clear();
    roomSet.addAll(mapperFacade.mapAsSet(hotelDto.getRoomSet(), Room.class));

    Set<Facilities> serviceSet = existedHotel.getFacilitiesSet();
    serviceSet.clear();
    serviceSet.addAll(mapperFacade.mapAsSet(hotelDto.getServiceSet(), Facilities.class));

    return existedHotel.getId();
  }

  @Override
  @Transactional
  public Long deleteHotel(Long id) {

    if (hotelRepository.findById(id).isPresent()) {
      hotelRepository.deleteById(id);
    } else {
      new HotelServiceException(EXCEPTION_MESSAGE);
    }

    return id;
  }
}
