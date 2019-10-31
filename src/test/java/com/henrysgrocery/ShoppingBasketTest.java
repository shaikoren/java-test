package com.henrysgrocery;

import org.junit.Test;

import static com.henrysgrocery.Product.SOUP;
import static org.assertj.core.api.Assertions.assertThat;

public class ShoppingBasketTest {
	private ShoppingBasket underTest = new ShoppingBasket();

	@Test
	public void shouldContainProducts() {
		underTest.addProduct(SOUP);
		assertThat(underTest.getProducts()).contains(SOUP);
	}
}
