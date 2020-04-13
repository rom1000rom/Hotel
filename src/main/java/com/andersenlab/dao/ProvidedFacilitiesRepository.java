package com.andersenlab.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.andersenlab.model.ProvidedFacilities;

public interface ProvidedFacilitiesRepository extends JpaRepository<ProvidedFacilities, Long> {

	Page<ProvidedFacilities> findAll(Pageable pageable);
}
