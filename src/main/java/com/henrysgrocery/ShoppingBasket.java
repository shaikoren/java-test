package com.henrysgrocery;

import java.util.ArrayList;
import java.util.List;

public class ShoppingBasket {

	private final List<Product> products = new ArrayList<>();

	public void addProduct(Product product) {
		products.add(product);
	}

	public List<Product> getProducts() {
		return products;
	}
}
