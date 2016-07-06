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
