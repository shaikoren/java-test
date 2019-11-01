package com.henrysgrocery.promotions;

import com.henrysgrocery.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.henrysgrocery.Product.*;


public class Promotion {

	private final Product product;
	private final Period period;
	private final Discount discount;

	Promotion(Product product, Period period, Discount discount) {
		this.product = product;
		this.period = period;
		this.discount = discount;
	}

	public boolean isActive(LocalDate dateOfPurchase) {
		return this.period.isWithinPeriod(dateOfPurchase);
	}

	Discount getDiscount() {
		return this.discount;
	}

	public Product getProduct() {
		return this.product;
	}


	public enum Discount {
		TEN_PERCENT_FOR_APPLES {
			@Override
			public BigDecimal applyDiscount(Map<Product, List<Product>> groupedProducts) {
				return groupedProducts.get(APPLE).stream().map(product -> product.getPrice().multiply(BigDecimal.valueOf(0.9)))
						.reduce(BigDecimal.ZERO, BigDecimal::add);
			}
		},
		BREAD_HALF_PRICE_FOR_TWO_SOUPS {
			@Override
			public BigDecimal applyDiscount(Map<Product, List<Product>> groupedProducts) {
				if (groupedProducts.get(SOUP) == null || groupedProducts.get(SOUP).size() < 2 || groupedProducts.get(BREAD) == null) {
					return BigDecimal.valueOf(0.0);
				}
				int numberOfDiscounts = groupedProducts.get(SOUP).size() / 2;

				List<BigDecimal> breadPrices = new ArrayList<>();
				List<Product> breads = groupedProducts.get(BREAD);

				breads.subList(0, numberOfDiscounts).forEach(bread -> {
					breadPrices.add(bread.getPrice().multiply(BigDecimal.valueOf(0.5)));
				});

				breads.subList(numberOfDiscounts, breads.size()).forEach(bread -> {
					breadPrices.add(bread.getPrice());
				});

				return breadPrices.stream().reduce(BigDecimal.ZERO, BigDecimal::add);

			}
		};

		public abstract BigDecimal applyDiscount(Map<Product, List<Product>> groupedProducts);
	}
}
