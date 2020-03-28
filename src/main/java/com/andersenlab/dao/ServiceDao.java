package com.andersenlab.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.andersenlab.model.Facilities;

interface ServiceDao extends JpaRepository<Facilities, Long> {

	Page<Facilities> findAll(Pageable pageable);

	Facilities findByFacilitiesName(String facilitiesName);

	Facilities findByFacilitiesNumber(String facilitiesNumber);

}
