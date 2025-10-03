package com.service;

import org.springframework.stereotype.Service;

import com.entity.Product;
import com.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductService {

	private final ProductRepository repositoryProduct;

	public ProductService(ProductRepository repositoryProduct) {

		this.repositoryProduct = repositoryProduct;
	}

	@Transactional
	public void save(Product product) {
		repositoryProduct.save(product);
	}

	@Transactional
	public void delete(Long id) {
		repositoryProduct.deleteById(id);
	}

}
