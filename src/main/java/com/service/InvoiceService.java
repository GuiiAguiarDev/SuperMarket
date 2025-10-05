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

		// Verifica se o produto que estamos procurando pelo nome existe, se nao existe
		// volta not found
		if (produtoEscolhdo == null) {
			System.out.println("Product not found");
			return;
		}

		// Verifica se a quantidade do produto que queremos comprar existe
		if (qtdProductBuyed <= 0) {
			System.out.println("Quantidade invalida");
			return;
		}

		// Aqui pega o produto que fizemos a busca pelo nome para ir especifico nele,
		// pois ele foi cadastrado no banco, eae acessamos um atributo especifico dele,
		// no caso o estoque
		// eae vejo se a quantidade do estoque dele é maior ou igual a quantidade que
		// quero comprar
		// que é o parametro que passei na minha função aqui, se for ou seja
		// produtoEscolhdo.getEstoque é 10 e a qtdProductBuyed é 5 então ele acessa,
		// pois tem
		if (produtoEscolhdo.getEstoque() >= qtdProductBuyed) {
			// Aqui quase a mesma coisa de cima porem aqui vamos seta um novo valor para o
			// estoque desse produto
			// que escolhemos, que é o seguinte o novo estoque dele vai ser o estoque dele -
			// a quantidade
			// que remos comprar, ou seja vamos supor que produtoEscolhdo é uma tv e o
			// estoque é 10
			// se passo a quantidade de 1 para compra agr lá no estoque vai ser 9, então no
			// setEstoque
			// estamos passandp 9, vai ser o novo valor, e caso compre de novo vai ser o
			// valor de 9 agr lá e
			// nao mais 10
			produtoEscolhdo.setEstoque(produtoEscolhdo.getEstoque() - qtdProductBuyed);
			// Aqui abaixo salvamos tudo isso para atualizar no banco
			System.out.println("Compra realizada com sucess!");
			productRepository.save(produtoEscolhdo);

			// Se o produto nao existe na lista de produtos é para diciona ele
			// Aqui setamos o relacionamento, pois estamos atribuindo a nota tbm que
			// cadastramos uma lista de produtos
			// ou seja vamos uper que é ssa invoice é a nota 158 então todos os produtos que
			// estou cadastrando
			// vai para essa nota 158
			if (!invoice.getProducts().contains(produtoEscolhdo)) {
				invoice.addProducts(produtoEscolhdo);
				double priceInvoice = qtdProductBuyed * produtoEscolhdo.getPrice();
				double princeInvoiceFinal = priceInvoice + priceInvoice;
				invoice.setPrice(princeInvoiceFinal);
			}
			// Salvamos aqui a notaSansungBook2

			invoiceRepository.save(invoice);

		} else {
			System.out.println("Erro");
		}

	}
}
