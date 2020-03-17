package com.andersenlab.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.andersenlab.model.Hotel;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelDao extends JpaRepository<Hotel, Long> {

  Page<Hotel> findAll(Pageable pageable);

  Hotel findByHotelName(String hotelName);
}
