package com.henrysgrocery.promotions;

import com.henrysgrocery.Product;

import java.math.BigDecimal;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.henrysgrocery.Product.*;
import static com.henrysgrocery.promotions.Promotion.Discount.TEN_PERCENT_FOR_APPLES;
import static java.time.LocalDate.now;
import static java.util.Arrays.asList;

public class CurrentPromotions {

	private List<Promotion> promotions = asList(
			new Promotion(APPLE,
					Period.between(now().plusDays(3), now().plusMonths(1).with(TemporalAdjusters.lastDayOfMonth())),
					TEN_PERCENT_FOR_APPLES),
			new Promotion(BREAD,
					Period.between(now().minusDays(1), now().plusDays(7)),
					Promotion.Discount.BREAD_HALF_PRICE_FOR_TWO_SOUPS)
	);

	public BigDecimal calculatePromotions(List<Promotion> relevantPromotions, Map<Boolean, List<Product>> productsByPromotions, Map<Product, List<Product>> groupedProducts) {
		Map<Product, List<Product>> promotionItemsByProduct = productsByPromotions.get(Boolean.TRUE).stream().collect(Collectors.groupingBy(Function.identity()));

		Map<Product, BigDecimal> discountedPrices = new HashMap<>(2);//remove hard coded

		for (Promotion promotion : relevantPromotions) {

			switch (promotion.getDiscount()) {
				case TEN_PERCENT_FOR_APPLES:
					discountedPrices.put(APPLE, promotionItemsByProduct.get(APPLE).stream().map(product -> product.getPrice().multiply(BigDecimal.valueOf(0.9)))
							.reduce(BigDecimal.ZERO, BigDecimal::add));
					break;
				case BREAD_HALF_PRICE_FOR_TWO_SOUPS:
					if (groupedProducts.get(SOUP) == null || groupedProducts.get(SOUP).size() < 2 || groupedProducts.get(BREAD) == null) {
						break;
					}
					int numberOfDiscounts = groupedProducts.get(SOUP).size() / 2;

					AtomicInteger counter = new AtomicInteger(0);

					List<BigDecimal> breadPrices = new ArrayList<>();
					groupedProducts.get(BREAD).forEach(bread -> {

						if (counter.intValue() < numberOfDiscounts) {
							breadPrices.add(bread.getPrice().multiply(BigDecimal.valueOf(0.5)));
							counter.incrementAndGet();
						} else {
							breadPrices.add(bread.getPrice());
						}
					});
					discountedPrices.put(BREAD, breadPrices.stream().reduce(BigDecimal.ZERO, BigDecimal::add));
					break;
			}
		}
		return discountedPrices.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public List<Promotion> getPromotions(){
		return this.promotions;
	}
}
