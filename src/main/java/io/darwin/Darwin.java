package io.darwin;

import io.darwin.harness.CurrentExecutionHarness;
import io.darwin.harness.EvolvedExecutionHarness;

import static io.darwin.Evolution.*;

/**
 * Created by fatfredyy on 19.12.15.
 */
public class Darwin<T> {

    //public EvolutionBuilder evolve(CurrentExecutionHarness oldExecutionHarness)

    private class EvolutionBuilder<T> {

        //public T to()
    }


    public Integer calculateOdds(Integer a, Integer b, Integer c) {
        if (a > b) {
            if (c > a) {
                return c;
            } else return a;
        } else {
            if (c > b) {
                return c;
            } else return b;
        }
    }

    public Integer calculateOdds2(Integer a, Integer b, Integer c) {
        return Math.max(Math.max(a, b), c);
    }


    public static void main(final String[] args) throws Exception {
        final Integer a =1, b =3, c = -1;
        final Darwin darwin = new Darwin();

        Integer mainMethod = Evolution.<Integer>of("mainMethod")
                .from(new CurrentExecutionHarness<Integer>() {
                    @Override
                    public Integer execute() throws Exception {
                        arguments(a, b, c);
                        return darwin.calculateOdds(a, b, c);
                    }
                })
                .to(new EvolvedExecutionHarness<Integer>() {
                    @Override
                    public Integer execute() throws Exception {
                        arguments(a, b, c);
                        return darwin.calculateOdds2(a, b, c);
                    }
                })
                .evolve();
    }


}
