package com.henrysgrocery;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.IntStream;

public class Shop {
	private final ShoppingBasket shopingBasket = new ShoppingBasket();
	private final Cashier cashier = new Cashier();
	private final Console console;

	 Shop(Console console) {
		this.console = console;
	}

	public void addToBasket(Product product, int quantity) {
		IntStream.range(0, quantity).forEach(i ->
				shopingBasket.addProduct(product)
		);
	}

	public void calculateTotal(LocalDate dateOfPurchase) {
		BigDecimal total = cashier.calculateTotal(shopingBasket, dateOfPurchase);
		console.print(String.format("%.2f", total));
	}
}
