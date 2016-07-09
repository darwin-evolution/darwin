/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2016, Darwin Project
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
