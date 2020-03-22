package com.andersenlab.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.andersenlab.model.ProvidedRoom;

public interface ProvidedRoomRepository extends JpaRepository<ProvidedRoom, Long> {

	Page<ProvidedRoom> findAll(Pageable pageable);
}
