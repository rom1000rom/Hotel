package com.andersenlab.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.andersenlab.model.Facilities;

@Repository
public interface FacilitiesRepository extends JpaRepository<Facilities, Long> {

	Page<Facilities> findAll(Pageable pageable);

	Facilities findByFacilitiesName(String facilitiesName);

	Facilities findByFacilitiesNumber(String facilitiesName);

}
