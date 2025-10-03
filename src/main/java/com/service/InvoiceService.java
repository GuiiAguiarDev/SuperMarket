package com.service;

import org.springframework.stereotype.Service;

import com.entity.Invoice;
import com.repository.InvoiceRepository;

import jakarta.transaction.Transactional;

@Service
public class InvoiceService {

	private final InvoiceRepository invoiceRepository;

	public InvoiceService(InvoiceRepository invoiceRepository) {

		this.invoiceRepository = invoiceRepository;
	}

	@Transactional
	public void save(Invoice invoice) {
		invoiceRepository.save(invoice);
	}

}
