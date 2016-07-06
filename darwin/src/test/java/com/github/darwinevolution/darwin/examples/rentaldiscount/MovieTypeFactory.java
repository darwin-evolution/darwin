package com.github.darwinevolution.darwin.examples.rentaldiscount;

public class MovieTypeFactory {

    public static MovieType from(Integer movieType) {
        if (movieType == 1) {
            return MovieType.COMEDY;
        }
        if (movieType == 2) {
            return MovieType.ROMANCE;
        }
        if (movieType == 3) {
            return MovieType.HORROR;
        }
        if (movieType == 4) {
            return MovieType.FAMILY;
        }
        throw new IllegalArgumentException("Unknown movie type");
    }
}
