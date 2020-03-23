package com.andersenlab.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.andersenlab.model.Invoice;

interface InvoiceDao extends JpaRepository<Invoice, Long> {

	Page<Invoice> findAll(Pageable pageable);

	Invoice findByInvoiceNumber(String invoiceNumber);

}
