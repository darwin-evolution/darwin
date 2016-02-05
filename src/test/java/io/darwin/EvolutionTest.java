/**
 * The MIT License
 *
 * Copyright (c) 2016 Darwin contributors
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
package io.darwin;

import io.darwin.execution.harness.CurrentExecutionHarness;
import io.darwin.execution.harness.EvolvedExecutionHarness;
import org.testng.annotations.Test;

public class EvolutionTest {

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


    @Test
    public void shouldEvolve() throws Exception {
        final Integer a = 1, b = 3, c = -1;

        Integer mainMethod = Evolution.<Integer>of("mainMethod")
                .from(new CurrentExecutionHarness<Integer>() {
                    @Override
                    public Integer execute() throws Exception {
                        arguments(a, b, c);
                        return calculateOdds(a, b, c);
                    }
                })
                .to(new EvolvedExecutionHarness<Integer>() {
                    @Override
                    public Integer execute() throws Exception {
                        arguments(a, b, c);
                        return calculateOdds2(a, b, c);
                    }
                })
                .evolve();
    }
}
