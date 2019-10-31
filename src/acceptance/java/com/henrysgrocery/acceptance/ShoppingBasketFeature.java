package com.henrysgrocery.acceptance;


import com.henrysgrocery.Console;
import com.henrysgrocery.Shop;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static com.henrysgrocery.Product.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingBasketFeature {

	@Mock
	private Console console;

	@InjectMocks
	private Shop shop;

	@Test
	public void can_calculate_basket_with_3_tins_of_soup_and_2_loafs_of_bread_bought_today(){

		shop.addToBasket(SOUP, 3);
		shop.addToBasket(BREAD, 2);
		shop.calculateTotal(LocalDate.now());

	    verify(console).print("3.15");
	}

	@Test
	public void can_calculate_basket_with_6_apples_and_milk_bought_today(){
		shop.addToBasket(APPLE, 6);
		shop.addToBasket(MILK, 1);
		shop.calculateTotal(LocalDate.now());

		verify(console).print("1.90");
	}

	@Test
	public void can_calculate_basket_with_6_apples_and_milk_bought_in_5_days(){
		shop.addToBasket(APPLE, 6);
		shop.addToBasket(MILK, 1);
		shop.calculateTotal(LocalDate.now().plusDays(5));

		verify(console).print("1.84");
	}

	@Test
	public void can_calculate_basket_with_3_apples_2_tins_of_soup_and_loaf_of_bread_bought_in_5_days(){
		shop.addToBasket(APPLE, 3);
		shop.addToBasket(SOUP, 2);
		shop.addToBasket(BREAD, 1);
		shop.calculateTotal(LocalDate.now().plusDays(5));

		verify(console).print("1.97");
	}

}
