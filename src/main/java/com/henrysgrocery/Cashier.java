package com.henrysgrocery;

import com.henrysgrocery.promotions.Period;
import com.henrysgrocery.promotions.Promotion;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.henrysgrocery.promotions.Promotion.Discount.TEN_PERCENT;
import static java.time.LocalDate.now;
import static java.util.Arrays.asList;

public class Cashier {

	private List<Promotion> promotions = asList(
			new Promotion(Product.APPLE,
					Period.between(now().plusDays(3), now().plusMonths(1).with(TemporalAdjusters.lastDayOfMonth())),
					TEN_PERCENT));


	public Double calculateTotal(ShoppingBasket shoppingBasket, LocalDate dateOfPurchase) {
		//calculate promotions
		Map<Product, List<Product>> itemsByProduct = shoppingBasket.getProducts().stream().collect(Collectors.groupingBy(Function.identity()));

		Map<Product, Double> productsTotalPrice = new HashMap<>(itemsByProduct.size()); 

		itemsByProduct.entrySet().forEach(entry -> {
			 double productPrice = 0.0;
			for (Promotion promotion : promotions) {
				if (promotion.getPeriod().isWithinPeriod(dateOfPurchase) && promotion.getProduct().equals(entry.getKey())) {
					                                             //only for apples
					productPrice = itemsByProduct.get(promotion.getProduct()).stream().mapToDouble(product -> product.getPrice() * 90 / 100).sum();
				} else{
					productPrice = entry.getValue().stream().mapToDouble(Product::getPrice).sum();
				}
			}
			productsTotalPrice.put(entry.getKey(),productPrice );
		});
		return productsTotalPrice.values().stream().mapToDouble(price -> price).sum();
	}


}
