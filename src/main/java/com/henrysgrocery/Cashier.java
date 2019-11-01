package com.henrysgrocery;

import com.henrysgrocery.promotions.ActivePromotions;
import com.henrysgrocery.promotions.Promotion;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.Boolean.FALSE;

public class Cashier {
	private ActivePromotions activePromotions = new ActivePromotions();


	public BigDecimal calculateTotal(ShoppingBasket shoppingBasket, LocalDate dateOfPurchase) {

		List<Promotion> relevantPromotions = filterActivePromotionsByPurchaseDate(dateOfPurchase);

		Map<Boolean, List<Product>> productsByPromotionApplicability = partitionProductsByPromotionApplicability(shoppingBasket, relevantPromotions);

		GroupedProducts groupedProducts = groupItemsInBasketByProduct(shoppingBasket);

		BigDecimal priceWithoutPromotion = totalPriceOfProductsWithoutPromotion(productsByPromotionApplicability);

		BigDecimal totalDiscountedPrices = activePromotions.calculatePromotions(relevantPromotions, groupedProducts);


		return totalDiscountedPrices.add(priceWithoutPromotion);
	}

	private GroupedProducts groupItemsInBasketByProduct(ShoppingBasket shoppingBasket) {
		Map<Product, List<Product>> groupedProducts = shoppingBasket.getProducts().stream().collect(Collectors.groupingBy(Function.identity()));
		return new GroupedProducts(groupedProducts);
	}

	private Map<Boolean, List<Product>> partitionProductsByPromotionApplicability(ShoppingBasket shoppingBasket, List<Promotion> relevantPromotions) {
		return shoppingBasket.getProducts().stream().collect(Collectors.partitioningBy(
				product -> relevantPromotions.stream().map(promotion -> promotion.getProduct()).collect(Collectors.toList()).contains(product)
		));
	}

	private List<Promotion> filterActivePromotionsByPurchaseDate(LocalDate dateOfPurchase) {
		return activePromotions.getPromotions().stream().filter(promotion -> promotion.isActive(dateOfPurchase)).collect(Collectors.toList());
	}

	private BigDecimal totalPriceOfProductsWithoutPromotion(Map<Boolean, List<Product>> productsByPromotions) {
		return productsByPromotions.get(FALSE).stream().map(Product::getPrice)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}


}
