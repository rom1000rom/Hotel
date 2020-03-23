package com.andersenlab.services;

import java.util.List;
import com.andersenlab.dto.HotelDto;

public interface HotelService {

  public List<HotelDto> findAllHotel();

  public HotelDto findHotelById(Long id);

  public Long saveHotel(HotelDto hotelDto);

  public Long updateHotel(HotelDto hotelDto);

  public Long deleteHotel(Long id);
}
