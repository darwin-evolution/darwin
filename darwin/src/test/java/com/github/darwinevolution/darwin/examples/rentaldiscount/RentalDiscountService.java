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


import com.github.darwinevolution.darwin.Evolution;
import com.github.darwinevolution.darwin.execution.harness.EvolvedExecutionHarness;
import com.github.darwinevolution.darwin.execution.harness.ProtoplastExecutionHarness;

public class RentalDiscountService {

    private RentalDiscountCalculator rentalDiscountCalculator;

    // we rule don't we?
    public Double calculateDiscountPercent(final Integer movieType, final Integer rentalPeriod) throws Exception {
        return Evolution.<Double>of("calculateDiscountPercent")
                .from(new ProtoplastExecutionHarness<Double>() {
                    @Override
                    public Double execute() throws Exception {
                        arguments(movieType, rentalPeriod);
                        return legacyCalculateDiscountPercent(movieType, rentalPeriod);
                    }
                }).to(new EvolvedExecutionHarness<Double>() {
                    @Override
                    public Double execute() throws Exception {
                        arguments(movieType, rentalPeriod);
                        return rentalDiscountCalculator.calculateDiscount(MovieTypeFactory.from(movieType), RentalPeriod.from(rentalPeriod)).doubleValue();
                    }
                }).evolve();
    }

    private Double legacyCalculateDiscountPercent(Integer movieType, Integer rentalPeriod) {
        if (movieType == 1 || (movieType != 3 && rentalPeriod == -1)) {
            return 10.0;
        } else if (movieType == 2 && rentalPeriod == 1) {
            return 40.0;
        } else if (movieType == 4 && rentalPeriod > 10) {
            return 5.0;
        } else {
            return 0.0;
        }
    }
}
