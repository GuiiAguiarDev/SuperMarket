package com;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.entity.Invoice;
import com.entity.Product;

import com.repository.InvoiceRepository;
import com.repository.ProductRepository;
import com.service.InvoiceService;
import com.service.ProductService;

@SpringBootApplication
public class SupermarketApplication {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		List<Product> listProducts = new ArrayList<>();

		var context = SpringApplication.run(SupermarketApplication.class, args);

		ProductService serviceProduct = context.getBean(ProductService.class);
		InvoiceService invoiceService = context.getBean(InvoiceService.class);
		InvoiceRepository invoiceRepository = context.getBean(InvoiceRepository.class);

		System.out.println("BEM VINDO AO SISTEMA - CADASTRO DE PRODUTOS");

		int resp = 1;

		while (resp == 1) {
			System.out.println("Qual o nome do produto?");
			String name = sc.next();
			System.out.println("Qual o estoque do produto?");
			int estoque = sc.nextInt();
			System.out.println("Qual valor únitario?");
			double priceUni = sc.nextDouble();
			Product prod = new Product(null, name, estoque, priceUni);
			serviceProduct.save(prod);
			System.out.println("PRODUTO SALVO!!");
			System.out.println("DESEJA CADASTRAR MAIS UM PRODUTO ? 1 - SIM / 2 - NÂO");
			resp = sc.nextInt();

			listProducts.add(prod);

		}

		System.out.println("---------------------------------");
		System.out.println("---------------------------------");
		System.out.println("---------------------------------");
		System.out.println("---------------------------------");

		Invoice invoice = new Invoice();
		// Gerando numero aleatorio para a nota fiscal
		int numberRandom = 1 + (int) (Math.random() * 9000);
		invoice.setInvoiceNumber(numberRandom);
		// Como nao temos o set na list pq é mal uso fazemos assim com for

		System.out.println("Area do Cliente, vamos comprar");
		System.out.println("");
		for (Product p : listProducts) {
			System.out.println("Nome do produto " + p.getName() + " - " + "Estoque " + p.getEstoque());
		}
		System.out.println("");

		int respClient = 1;
		while (respClient == 1) {
			System.out.println("Qual nome do produto gostaria de comprar");
			String nameChanged = sc.next();

			System.out.println("Qual a quantidade do produto");
			int qtdProduct = sc.nextInt();

			invoiceService.comprar(nameChanged, qtdProduct, invoice);

			System.out.println("Deseja comprar mais produtos 1-sim 2- nao");
			respClient = sc.nextInt();

		}

		sc.close();

	}

}