package com.henrysgrocery.promotions;

import com.henrysgrocery.Product;
import lombok.Value;


@Value
public class Promotion {

	private Product product;
	private Period period;
	private Discount discount;


	public enum Discount{
		TEN_PERCENT,
		BREAD_HALF_PRICE
	}
}
