package com.henrysgrocery;

import org.junit.Test;

import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;

public class CashierTest {
	private Cashier underTest = new Cashier();
	private ShoppingBasket shoppingBasket = new ShoppingBasket();

	@Test
	public void shouldCalculatePriceWithoutDiscount() {

		shoppingBasket.addProduct(Product.BREAD);
		shoppingBasket.addProduct(Product.SOUP);
		shoppingBasket.addProduct(Product.MILK);
		shoppingBasket.addProduct(Product.APPLE);

		Double result = underTest.calculateTotal(shoppingBasket, now());

		assertThat(result).isEqualTo(2.85);
	}

	@Test
	public void shouldCalculatePromotionForApplesWithinPeriod() {

		shoppingBasket.addProduct(Product.APPLE);

		Double result = underTest.calculateTotal(shoppingBasket, now().plusDays(7));

		assertThat(result).isEqualTo(0.09);
	}

	@Test
	public void shouldNotApplyPromotionForApplesOutOfPeriod(){
		shoppingBasket.addProduct(Product.APPLE);
		shoppingBasket.addProduct(Product.APPLE);

		Double result = underTest.calculateTotal(shoppingBasket, now());

		assertThat(result).isEqualTo(0.20);

	}

}
