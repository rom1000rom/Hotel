package com.andersenlab.dao;

import java.awt.print.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.andersenlab.model.Service;

interface ServiceDao extends JpaRepository<Service, Long> {

	Page<Service> findAll(Pageable pageable);

	Service findByServiceName(String serviceName);

	Service findByServiceNumber(String serviceNumber);

}
