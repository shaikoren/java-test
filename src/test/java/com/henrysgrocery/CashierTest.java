package com.henrysgrocery;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static com.henrysgrocery.Product.*;
import static java.time.LocalDate.now;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class CashierTest {
	private Cashier underTest = new Cashier();
	private ShoppingBasket shoppingBasket = new ShoppingBasket();

	private final List<Product> products;
	private final LocalDate dateOfPurchase;
	private final double expected;

	public CashierTest(List<Product> products, LocalDate dateOfPurchase, double expected) {
		this.products = products;
		this.dateOfPurchase = dateOfPurchase;
		this.expected = expected;
	}


	@Parameters (name = "{index}: when purchasing {0} on {1}, the price should be {2}")
	public static Collection<Object[]> data() {
		return asList(new Object[][]{
				{asList(BREAD, SOUP, MILK, APPLE), now().plusMonths(2), 2.85},
				{asList(APPLE), now().plusDays(7), 0.09},
				{asList(APPLE, APPLE), now() , 0.20},
				{asList(SOUP, SOUP, BREAD), now(), 1.70},
				{asList(SOUP, SOUP, SOUP, BREAD, BREAD), now(), 3.15},
				{asList(APPLE, APPLE, APPLE, APPLE, APPLE,APPLE, MILK), now(), 1.90}
		});
	}


	@Test
	public void shouldCalculateTotalPrice() {
		products.forEach(product -> shoppingBasket.addProduct(product));
		
		Double result = underTest.calculateTotal(shoppingBasket, dateOfPurchase);

		assertThat(result).isEqualTo(expected);
	}

}
