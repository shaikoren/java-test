package com.henrysgrocery;

import java.math.BigDecimal;

public enum Product {
	SOUP("tin", BigDecimal.valueOf(0.65)),
	BREAD("loaf", BigDecimal.valueOf(0.80)),
	MILK("bottle", BigDecimal.valueOf(1.30)),
	APPLE("single", BigDecimal.valueOf(0.10));

	private final String unit;
	private final BigDecimal price;

	Product(String unit, BigDecimal price) {
		this.unit = unit;
		this.price = price;
	}

	BigDecimal getPrice(){
		return price;
	}
}
