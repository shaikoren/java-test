package com.henrysgrocery;

public enum Product {
	SOUP("tin", 0.65),
	BREAD("loaf", 0.80);

	private final String unit;
	private final double price;

	Product(String unit, double price) {
		this.unit = unit;
		this.price = price;
	}
}
