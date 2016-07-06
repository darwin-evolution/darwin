package com.github.darwinevolution.darwin.examples.rentaldiscount;

public class RentalPeriod {

    private Integer rentalPeriod;

    private RentalPeriod(Integer rentalPeriod) {
        this.rentalPeriod = rentalPeriod;
    }

    public boolean isWeekend() {
        return rentalPeriod == -1;
    }

    public boolean isOverNight() {
        return rentalPeriod == 1;
    }

    public boolean isLongRental() {
        return rentalPeriod > 10;
    }

    public static RentalPeriod from(Integer rentalPeriod) {
        return  new RentalPeriod(rentalPeriod);
    }
}
