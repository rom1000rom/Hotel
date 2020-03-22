package com.andersenlab.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.andersenlab.model.Facilities;

interface FacilitiesRepository extends JpaRepository<Facilities, Long> {

	Page<Facilities> findAll(Pageable pageable);

	Facilities findByServiceName(String serviceName);

	Facilities findByServiceNumber(String serviceNumber);

}
