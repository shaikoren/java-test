package com.henrysgrocery.promotions;

import com.henrysgrocery.GroupedProducts;
import com.henrysgrocery.Product;

import java.math.BigDecimal;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.henrysgrocery.Product.APPLE;
import static com.henrysgrocery.Product.BREAD;
import static com.henrysgrocery.promotions.Discount.TEN_PERCENT_FOR_APPLES;
import static java.time.LocalDate.now;
import static java.util.Arrays.asList;

public class ActivePromotions {

	private List<Promotion> promotions = asList(
			new Promotion(APPLE,
					Period.between(now().plusDays(3), now().plusMonths(1).with(TemporalAdjusters.lastDayOfMonth())),
					TEN_PERCENT_FOR_APPLES),
			new Promotion(BREAD,
					Period.between(now().minusDays(1), now().plusDays(7)),
					Discount.BREAD_HALF_PRICE_FOR_TWO_SOUPS)
	);

	public BigDecimal calculatePromotions(List<Promotion> relevantPromotions, GroupedProducts groupedProducts) {

		Map<Product, BigDecimal> discountedPrices = new HashMap<>(promotions.size());

		relevantPromotions.forEach(promotion -> {

			discountedPrices.put(promotion.getProduct(), promotion.getDiscount().applyDiscount(groupedProducts));
		});

		return discountedPrices.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
	}


	public List<Promotion> getPromotions() {
		return this.promotions;
	}
}
