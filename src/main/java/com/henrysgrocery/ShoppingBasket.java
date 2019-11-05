package com.henrysgrocery;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ShoppingBasket {

	private final List<Product> products = new ArrayList<>();

	public void addProduct(Product product) {
		products.add(product);
	}

	public List<Product> getProducts() {
		return products;
	}

	public List<Product> getProductsOf(Product product) {
		return getGroupedProducts().get(product);
	}

	public boolean hasLessThenMinimumNumberOfProducts(Product product, int minimumNumberOfItems) {
		return getProductsOf(product) == null || getProductsOf(product).size() < minimumNumberOfItems;
	}

	private Map<Product, List<Product>> getGroupedProducts() {
		return this.getProducts().stream().collect(Collectors.groupingBy(Function.identity()));
	}
}
