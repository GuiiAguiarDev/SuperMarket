package com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.entity.Invoice;
import com.entity.Product;
import com.repository.InvoiceRepository;
import com.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class InvoiceService {

	private final InvoiceRepository invoiceRepository;
	private final ProductRepository productRepository;

	public InvoiceService(InvoiceRepository invoiceRepository, ProductRepository productRepository) {

		this.invoiceRepository = invoiceRepository;
		this.productRepository = productRepository;
	}

	@Transactional
	public void save(Invoice invoice) {
		invoiceRepository.save(invoice);
	}

	public void comprar(String nameProduct, int qtdProductBuyed, Invoice invoice) {
		// Como vamos escolher o produto pelo name fazemos assim, no main estamos
		// buscando
		// o produto que queremos comprar pelo nome ent para funcionar faço o codigo
		// abaixo
		Product produtoEscolhdo = productRepository.findAll().stream()
				.filter(p -> p.getName().equalsIgnoreCase(nameProduct)).findFirst().orElse(null);

		if (produtoEscolhdo == null) {
			System.out.println("Product not found");
			return;
		}

		if (qtdProductBuyed <= 0) {
			System.out.println("Quantidade invalida");
			return;
		}

		if (produtoEscolhdo.getEstoque() >= qtdProductBuyed) {
			produtoEscolhdo.setEstoque(produtoEscolhdo.getEstoque() - qtdProductBuyed);
			System.out.println("Compra realizada com sucess!");
			productRepository.save(produtoEscolhdo);

			
			if(!invoice.getProducts().contains(produtoEscolhdo)) {
				invoice.addProducts(produtoEscolhdo);

			}
			
			invoiceRepository.save(invoice);

		} else {
			System.out.println("Erro");
		}

	}
}
