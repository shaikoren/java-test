package com.henrysgrocery.promotions;

import org.junit.Test;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;

public class PeriodTest {

	@Test
	public void shouldDetermineIfDateWithinPeriod(){
		Period underTest = Period.between(LocalDate.now().plusDays(3), now().plusMonths(1).with(TemporalAdjusters.lastDayOfMonth()));

	    assertThat(underTest.isWithinPeriod(now())).isFalse();
	    assertThat(underTest.isWithinPeriod(now().plusDays(7))).isTrue();
	    assertThat(underTest.isWithinPeriod(now().plusMonths(2))).isFalse();
	}
}