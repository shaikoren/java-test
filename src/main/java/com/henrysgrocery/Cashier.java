package com.henrysgrocery;

public class Cashier {

	public Double calculateTotal(ShoppingBasket shoppingBasket) {
		return shoppingBasket.getProducts().stream().mapToDouble(Product::getPrice).sum();
	}
}
