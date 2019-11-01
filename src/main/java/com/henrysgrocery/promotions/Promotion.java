package com.henrysgrocery.promotions;

import com.henrysgrocery.Product;

import java.time.LocalDate;


public class Promotion {

	private final Product product;
	private final Period period;
	private final Discount discount;

	public Promotion(Product product, Period period, Discount discount) {
		this.product = product;
		this.period = period;
		this.discount = discount;
	}

	public boolean isActive(LocalDate dateOfPurchase) {
		return this.period.isWithinPeriod(dateOfPurchase);
	}

	public Discount getDiscount() {
		return this.discount;
	}

	public Product getProduct() {
		return this.product;
	}


	public enum Discount{
		TEN_PERCENT_FOR_APPLES,
		BREAD_HALF_PRICE_FOR_TWO_SOUPS
	}
}
