package com.henrysgrocery;

public enum Product {
	SOUP("tin", 0.65),
	BREAD("loaf", 0.80),
	MILK("bottle", 1.30),
	APPLE("single", 0.10);

	private final String unit;
	private final double price;

	Product(String unit, double price) {
		this.unit = unit;
		this.price = price;
	}

	Double getPrice(){
		return price;
	}
}
