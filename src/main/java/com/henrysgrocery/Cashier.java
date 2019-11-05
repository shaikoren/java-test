package com.henrysgrocery;

import com.henrysgrocery.promotions.ActivePromotions;
import com.henrysgrocery.promotions.Promotion;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Boolean.FALSE;

public class Cashier {
	private ActivePromotions activePromotions = new ActivePromotions();


	public BigDecimal calculateTotal(ShoppingBasket shoppingBasket, LocalDate dateOfPurchase) {
		List<Promotion> relevantPromotions = filterActivePromotionsByPurchaseDate(dateOfPurchase);
		BigDecimal priceWithoutPromotion = totalPriceOfProductsWithoutPromotion(productsWithoutPromotions(shoppingBasket, relevantPromotions));
		BigDecimal discountedPrices = activePromotions.calculatePromotions(relevantPromotions, shoppingBasket);

		return discountedPrices.add(priceWithoutPromotion);
	}

	private List<Product> productsWithoutPromotions(ShoppingBasket shoppingBasket, List<Promotion> relevantPromotions) {
		Map<Boolean, List<Product>> productsGroupedByPromotionApplicability = shoppingBasket.getProducts().stream().collect(Collectors.partitioningBy(
				product -> relevantPromotions.stream().map(promotion -> promotion.getProduct()).collect(Collectors.toList()).contains(product)
		));
		return productsGroupedByPromotionApplicability.get(FALSE);
	}

	private List<Promotion> filterActivePromotionsByPurchaseDate(LocalDate dateOfPurchase) {
		return activePromotions.getPromotions().stream().filter(promotion -> promotion.isActive(dateOfPurchase)).collect(Collectors.toList());
	}

	private BigDecimal totalPriceOfProductsWithoutPromotion( List<Product> productsWithoutPromotions) {
		return productsWithoutPromotions.stream().map(Product::getPrice)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}


}
