package com.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_INVOICE")
public class Invoice {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "TB_ID")
	private Long id;
	@Column(name = "TB_INVOICE_NUMBER")
	private Integer InvoiceNumber;
	@Column(name = "TB_PRICE")
	private Double price;

	@OneToMany
	private List<Product> products = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getInvoiceNumber() {
		return InvoiceNumber;
	}

	public void setInvoiceNumber(Integer invoiceNumber) {
		InvoiceNumber = invoiceNumber;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void addProducts(Product Products) {
		this.products.add(Products);
		//Não precisa do set aqui fica em branco pq é unidirecional so um lado conhec eo outro
	}

}
