package com.henrysgrocery;

import com.henrysgrocery.promotions.CurrentPromotions;
import com.henrysgrocery.promotions.Promotion;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.Boolean.FALSE;

public class Cashier {
	private CurrentPromotions currentPromotions = new CurrentPromotions();


	public Double calculateTotal(ShoppingBasket shoppingBasket, LocalDate dateOfPurchase) {

		List<Promotion> relevantPromotions = filterRelevantPromotionsByPurchaseDate(dateOfPurchase);

		Map<Boolean, List<Product>> productsByPromotionApplicability = partitionProductsByPromotionApplicability(shoppingBasket, relevantPromotions);

		Map<Product, List<Product>> groupedProducts = groupItemsInBasketByProduct(shoppingBasket);

		BigDecimal priceWithoutPromotion = totalPriceOfProductsWithoutPromotion(productsByPromotionApplicability);

		BigDecimal totalDiscountedPrices = currentPromotions.calculatePromotions(relevantPromotions, productsByPromotionApplicability, groupedProducts);


		return totalDiscountedPrices.add(priceWithoutPromotion).doubleValue();
	}

	private Map<Product, List<Product>> groupItemsInBasketByProduct(ShoppingBasket shoppingBasket) {
		return shoppingBasket.getProducts().stream().collect(Collectors.groupingBy(Function.identity()));
	}

	private Map<Boolean, List<Product>> partitionProductsByPromotionApplicability(ShoppingBasket shoppingBasket, List<Promotion> relevantPromotions) {
		return shoppingBasket.getProducts().stream().collect(Collectors.partitioningBy(
				product -> relevantPromotions.stream().map(promotion -> promotion.getProduct()).collect(Collectors.toList()).contains(product)
		));
	}

	private List<Promotion> filterRelevantPromotionsByPurchaseDate(LocalDate dateOfPurchase) {
		return currentPromotions.getPromotions().stream().filter(promotion -> promotion.getPeriod().isWithinPeriod(dateOfPurchase)).collect(Collectors.toList());
	}

	private BigDecimal totalPriceOfProductsWithoutPromotion(Map<Boolean, List<Product>> productsByPromotions) {
		return productsByPromotions.get(FALSE).stream().map(Product::getPrice)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}


}
