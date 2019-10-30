package com.henry.grocery.acceptance;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ShoppingBasketFeature {

	@Test
	public void shouldOnlyRunWhenUnitPass(){
	    assertThat(1+ 1).isEqualTo(2);
	}
}
