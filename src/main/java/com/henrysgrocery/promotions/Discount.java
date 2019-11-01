package com.henrysgrocery.promotions;

import com.henrysgrocery.GroupedProducts;
import com.henrysgrocery.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.henrysgrocery.Product.*;

public enum Discount {

	TEN_PERCENT_FOR_APPLES {
		@Override
		public BigDecimal applyDiscount(GroupedProducts groupedProducts) {
			return groupedProducts.getProductsOf(APPLE).stream().map(product -> product.getPrice().multiply(BigDecimal.valueOf(0.9)))
					.reduce(BigDecimal.ZERO, BigDecimal::add);
		}
	},
	BREAD_HALF_PRICE_FOR_TWO_SOUPS {
		@Override
		public BigDecimal applyDiscount(GroupedProducts groupedProducts) {
			if ( groupedProducts.hasLessThenMinimumNumberOfProducts(SOUP, 2) ||
					groupedProducts.hasLessThenMinimumNumberOfProducts(BREAD, 1)) {
				return BigDecimal.valueOf(0.0);
			}
			int numberOfDiscounts = groupedProducts.getProductsOf(SOUP).size() / 2;

			List<BigDecimal> breadPrices = new ArrayList<>();
			List<Product> breads = groupedProducts.getProductsOf(BREAD);

			breads.subList(0, numberOfDiscounts).forEach(bread -> {
				breadPrices.add(bread.getPrice().multiply(BigDecimal.valueOf(0.5)));
			});

			breads.subList(numberOfDiscounts, breads.size()).forEach(bread -> {
				breadPrices.add(bread.getPrice());
			});

			return breadPrices.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
		}
	};

	public abstract BigDecimal applyDiscount(GroupedProducts groupedProducts);


}

