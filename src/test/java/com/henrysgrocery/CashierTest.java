package com.henrysgrocery;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CashierTest {
	private Cashier underTest = new Cashier();

	@Test
	public void shouldCalculatePriceWithoutDiscount() {

		ShoppingBasket shoppingBasket = new ShoppingBasket();
		shoppingBasket.addProduct(Product.BREAD);
		shoppingBasket.addProduct(Product.SOUP);
		shoppingBasket.addProduct(Product.MILK);
		shoppingBasket.addProduct(Product.APPLE);

		Double result = underTest.calculateTotal(shoppingBasket);

		assertThat(result).isEqualTo(2.85);
	}
}
