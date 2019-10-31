package com.henrysgrocery;

import com.henrysgrocery.promotions.Period;
import com.henrysgrocery.promotions.Promotion;

import java.math.BigDecimal;
import java.time.LocalDate;
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

public class Cashier {

	private List<Promotion> promotions = asList(
			new Promotion(APPLE,
					Period.between(now().plusDays(3), now().plusMonths(1).with(TemporalAdjusters.lastDayOfMonth())),
					TEN_PERCENT_FOR_APPLES),
			new Promotion(BREAD,
					Period.between(now().minusDays(1), now().plusDays(7)),
					Promotion.Discount.BREAD_HALF_PRICE)
	);


	public Double calculateTotal(ShoppingBasket shoppingBasket, LocalDate dateOfPurchase) {

		//filter promotions by date
		List<Promotion> relevantPromotions = promotions.stream().filter(promotion -> promotion.getPeriod().isWithinPeriod(dateOfPurchase)).collect(Collectors.toList());

		List<Product> productsOfRelevantPromotion = relevantPromotions.stream().map(promotion -> promotion.getProduct()).collect(Collectors.toList());


		Map<Boolean, List<Product>> productsByPromotions = shoppingBasket.getProducts().stream().collect(Collectors.partitioningBy(
				product -> productsOfRelevantPromotion.contains(product)
		));

		Map<Product, List<Product>> groupedProducts = shoppingBasket.getProducts().stream().collect(Collectors.groupingBy(Function.identity()));

		//calculate price for products without promotion
		BigDecimal priceWithoutPromotion = productsByPromotions.get(Boolean.FALSE).stream().map(Product::getPrice)
				.reduce(BigDecimal.ZERO, BigDecimal::add);


		//calculate promotions
		Map<Product, List<Product>> promotionItemsByProduct = productsByPromotions.get(Boolean.TRUE).stream().collect(Collectors.groupingBy(Function.identity()));

		Map<Product, BigDecimal> discountedPrices = new HashMap<>(2);//remove hard coded

		for (Promotion promotion : relevantPromotions) {

			switch (promotion.getDiscount()) {
				case TEN_PERCENT_FOR_APPLES:
					//only for apples
					discountedPrices.put(APPLE, promotionItemsByProduct.get(APPLE).stream().map(product -> product.getPrice().multiply(BigDecimal.valueOf(0.9)))
					.reduce(BigDecimal.ZERO, BigDecimal::add));
					break;
				case BREAD_HALF_PRICE:
					if (groupedProducts.get(SOUP) == null || groupedProducts.get(SOUP).size() < 2 || groupedProducts.get(BREAD) == null) {
						break;
					}
					int numberOfSoupTins = groupedProducts.get(SOUP).size();
					int numberOfDiscounts = numberOfSoupTins / 2;

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
		return discountedPrices.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add)
				.add(priceWithoutPromotion).doubleValue();
	}


}
