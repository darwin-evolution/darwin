package com.github.darwinevolution.darwin.examples.rentaldiscount;

import java.math.BigDecimal;

public class RentalDiscountCalculator {

    private static final BigDecimal NO_DISCOUNT = BigDecimal.ZERO;
    private static final BigDecimal WEEKEND_DISCOUNT = BigDecimal.TEN;
    private static final BigDecimal ROMANTIC_EVENING_DISCOUNT = new BigDecimal(40.0);
    private static final BigDecimal FAMILY_WEEK = new BigDecimal(5.0);

    //piece of art, not code...
    public BigDecimal calculateDiscount(MovieType movieType, RentalPeriod rentalPeriod) {
        if (movieType.equals(MovieType.COMEDY)
                || (!movieType.equals(MovieType.HORROR) && rentalPeriod.isWeekend())) {
            return WEEKEND_DISCOUNT;
        }

        if (movieType.equals(MovieType.ROMANCE) && rentalPeriod.isOverNight()) {
            return ROMANTIC_EVENING_DISCOUNT;
        }

        if (movieType.equals(MovieType.FAMILY) && rentalPeriod.isLongRental())  {
            return FAMILY_WEEK;
        }

        return NO_DISCOUNT;
    }
}
