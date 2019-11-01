package com.henrysgrocery;

import java.util.List;
import java.util.Map;

public class GroupedProducts {

	private final Map<Product, List<Product>> groupedProducts;

	GroupedProducts(Map<Product, List<Product>> groupedProducts) {
		this.groupedProducts = groupedProducts;
	}

	public List<Product> getProductsOf(Product product) {
		return groupedProducts.get(product);
	}

	public boolean hasLessThenMinimumNumberOfProducts(Product product, int minimumNumberOfItems) {
		return groupedProducts.get(product) == null || groupedProducts.get(product).size() < minimumNumberOfItems;
	}
}
