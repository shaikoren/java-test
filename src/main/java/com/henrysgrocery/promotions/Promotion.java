package com.henrysgrocery.promotions;

import com.henrysgrocery.Product;
import com.henrysgrocery.ShoppingBasket;

import java.math.BigDecimal;
import java.time.LocalDate;


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

	public Product getProduct() {
		return this.product;
	}

	 BigDecimal applyDiscount(ShoppingBasket shoppingBasket){
		     return this.discount.applyDiscount(shoppingBasket);
	}

}
