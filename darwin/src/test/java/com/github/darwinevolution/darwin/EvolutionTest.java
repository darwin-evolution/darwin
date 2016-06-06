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
package com.github.darwinevolution.darwin;

import com.github.darwinevolution.darwin.api.EvolutionResultConsumer;
import com.github.darwinevolution.darwin.api.ResultConsumerConfiguration;
import com.github.darwinevolution.darwin.execution.harness.ProtoplastExecutionHarness;
import com.github.darwinevolution.darwin.execution.harness.EvolvedExecutionHarness;
import com.github.darwinevolution.darwin.execution.result.ResultType;
import com.github.darwinevolution.darwin.execution.result.comparison.ComparisonResult;
import com.github.darwinevolution.darwin.execution.result.protoplast.ProtoplastValueExecutionResult;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

public class EvolutionTest {

    private Integer calculateMax(Integer a, Integer b, Integer c) {
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

    private Integer calculateMax2(Integer a, Integer b, Integer c) {
        return Math.max(Math.max(a, b), c);
    }

    @Test
    public void shouldEvolve() throws Exception {
        final Integer a = 1, b = 3, c = -1;

        Integer max = Evolution.<Integer>of("calculateMax")
                .from(new ProtoplastExecutionHarness<Integer>() {
                    @Override
                    public Integer execute() throws Exception {
                        arguments(a, b, c);
                        return calculateMax(a, b, c);
                    }
                })
                .to(new EvolvedExecutionHarness<Integer>() {
                    @Override
                    public Integer execute() throws Exception {
                        arguments(a, b, c);
                        return calculateMax2(a, b, c);
                    }
                })
                .evolve();
    }

    @Test
    public void commonsLangBackwardCompatibility() throws Exception {
        final String value = "some definitely not blank string";

        Boolean isBlank = Evolution.<Boolean>of("StringUtilsIsBlank")
                .withEvolutionResultConsumer(new EvolutionResultConsumer<Boolean>() {
                    @Override
                    public void consumeResults(ComparisonResult<Boolean> comparisonResult, ResultConsumerConfiguration resultConsumerConfiguration) {
                        Assertions.assertThat(comparisonResult.getResultType()).isEqualTo(ResultType.OK);
                    }
                })
                .from(new ProtoplastExecutionHarness<Boolean>() {
                    @Override
                    public Boolean execute() throws Exception {
                        arguments(value);
                        return StringUtils.isBlank(value);
                    }
                })
                .to(new EvolvedExecutionHarness<Boolean>() {
                    @Override
                    public Boolean execute() throws Exception {
                        arguments(value);
                        return org.apache.commons.lang3.StringUtils.isBlank(value);
                    }
                })
                .evolve();

        List allInterfaces = Evolution.<List>of("allInterfaces")
                .withEvolutionResultConsumer(new EvolutionResultConsumer<List>() {
                    @Override
                    public void consumeResults(ComparisonResult<List> comparisonResult, ResultConsumerConfiguration resultConsumerConfiguration) {
                        Assertions.assertThat(comparisonResult.getResultType()).isEqualTo(ResultType.OK);
                    }
                })
                .from(new ProtoplastExecutionHarness<List>() {
                    @Override
                    public List execute() throws Exception {
                        return ClassUtils.getAllInterfaces(ProtoplastValueExecutionResult.class);
                    }
                }).to(new EvolvedExecutionHarness<List>() {
                    @Override
                    public List execute() throws Exception {
                        return org.apache.commons.lang3.ClassUtils.getAllInterfaces(ProtoplastValueExecutionResult.class);
                    }
                }).evolve();
    }
}
