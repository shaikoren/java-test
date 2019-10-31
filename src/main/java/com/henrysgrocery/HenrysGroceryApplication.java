package com.henrysgrocery;

import static java.time.LocalDate.now;

public class HenrysGroceryApplication {

	public static void main(String[] args) {
		Shop shop = new Shop(new Console());

		shop.addToBasket(Product.APPLE, 2);
		shop.addToBasket(Product.SOUP, 3);
		shop.addToBasket(Product.MILK, 1);
		shop.calculateTotal(now());
	}
}
