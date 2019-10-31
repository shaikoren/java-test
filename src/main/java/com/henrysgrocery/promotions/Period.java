package com.henrysgrocery.promotions;

import java.time.LocalDate;


public class Period {
	private final LocalDate from;
	private final LocalDate until;

	private Period(LocalDate from, LocalDate until) {
		this.from = from;
		this.until = until;
	}

	public static Period between(LocalDate from, LocalDate until) {
		return new Period(from, until);
	}

	public boolean isWithinPeriod(LocalDate date) {
		return date.isAfter(from) && date.isBefore(until);
	}
}
