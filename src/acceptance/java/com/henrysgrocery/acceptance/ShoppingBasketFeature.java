package com.henrysgrocery.acceptance;


import com.henrysgrocery.Console;
import com.henrysgrocery.Shop;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static com.henrysgrocery.Product.BREAD;
import static com.henrysgrocery.Product.SOUP;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingBasketFeature {

	private Shop shop = new Shop();
	@Mock
	private Console console;

	@Test
	public void can_add_items_and_calculate_total_price(){

		shop.addToBasket(SOUP, 3);
		shop.addToBasket(BREAD, 2);
		shop.calculateTotal(LocalDateTime.now());

	    verify(console).print("3.15");
	}
}
