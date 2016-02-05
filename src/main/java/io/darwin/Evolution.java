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

import io.darwin.api.EvolutionResultConsumer;
import io.darwin.api.ImplementationChooser;
import io.darwin.async.AsyncEvolutionBuilder;
import io.darwin.execution.HarnessExecutor;
import io.darwin.execution.ImplementationPreferenceType;
import io.darwin.execution.harness.CurrentExecutionHarness;
import io.darwin.execution.harness.EvolvedExecutionHarness;
import io.darwin.execution.result.ExecutionResult;
import io.darwin.execution.result.ResultType;
import io.darwin.execution.result.comparison.ComparisonResult;
import io.darwin.execution.result.comparison.ResultComparator;
import io.darwin.slf4j.Slf4jEvolutionResultConsumer;
import io.darwin.typesafeconfig.TypesafeConfigImplementationChooser;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Evolution {

    public static <T> EvolutionContext<T> of(String name) {
        return new EvolutionContext<T>(name);
    }

    public static AsyncEvolutionBuilder.EvolutionTimeout ms(Long evolutionTimeout) {
        return new AsyncEvolutionBuilder.EvolutionTimeout(evolutionTimeout, TimeUnit.MILLISECONDS);
    }

    public static class FromEvolutionBuilder<T> {

        private EvolutionContext<T> evolutionContext;
        private CurrentExecutionHarness<T> currentExecutionHarness;

        public FromEvolutionBuilder(EvolutionContext<T> evolutionContext, CurrentExecutionHarness<T> currentExecutionHarness) {
            this.evolutionContext = evolutionContext;
            this.currentExecutionHarness = currentExecutionHarness;
        }

        public EvolutionBuilder<T> to(EvolvedExecutionHarness<T> evolvedExecutionHarness) {
            return new EvolutionBuilder<T>(evolutionContext, currentExecutionHarness, evolvedExecutionHarness);
        }
    }

    public static class EvolutionBuilder<T> {

        private EvolutionContext<T> evolutionContext;
        private CurrentExecutionHarness<T> currentExecutionHarness;
        private EvolvedExecutionHarness<T> evolvedExecutionHarness;
        private HarnessExecutor<T> harnessExecutor;
        private ResultComparator<T> resultComparator;

        public EvolutionBuilder(EvolutionContext<T> evolutionContext, CurrentExecutionHarness<T> currentExecutionHarness, EvolvedExecutionHarness<T> evolvedExecutionHarness) {
            this.evolutionContext = evolutionContext;
            this.currentExecutionHarness = currentExecutionHarness;
            this.evolvedExecutionHarness = evolvedExecutionHarness;
            this.harnessExecutor = new HarnessExecutor<T>();
            this.resultComparator = new ResultComparator<T>();
        }

        public T evolve() throws Exception {
            ImplementationPreferenceType implementationPreferenceType = evolutionContext.implementationChooser.chooseImplementation(evolutionContext.name);

            ExecutionResult<T> currentExecutionResult = harnessExecutor.executeHarness(currentExecutionHarness);

            ExecutionResult<T> evolvedExecutionResult = harnessExecutor.executeHarness(evolvedExecutionHarness);

            consumeResults(implementationPreferenceType, currentExecutionResult, evolvedExecutionResult);

            ExecutionResult<T> preferredImplementationResult = implementationPreferenceType.equals(ImplementationPreferenceType.EVOLVED) ? evolvedExecutionResult : currentExecutionResult;

            if (preferredImplementationResult.getException() == null) {
                return preferredImplementationResult.getResult();
            } else {
                throw preferredImplementationResult.getException();
            }
        }

        private void consumeResults(ImplementationPreferenceType implementationPreferenceType, ExecutionResult<T> currentExecutionResult, ExecutionResult<T> evolvedExecutionResult) {
            ComparisonResult<T> comparisonResult = resultComparator.compareResults(evolutionContext.name, currentExecutionResult.getArguments(), implementationPreferenceType, currentExecutionResult, evolvedExecutionResult);
            evolutionContext.evolutionResultConsumer.consumeResults(comparisonResult);
        }

    }

    public static class EvolutionContext<T> {
        private String name;
        private ImplementationChooser implementationChooser;
        private EvolutionResultConsumer<T> evolutionResultConsumer;

        public EvolutionContext(String name) {
            this.name = name;
            this.implementationChooser = new TypesafeConfigImplementationChooser();
            this.evolutionResultConsumer = new Slf4jEvolutionResultConsumer<T>();
        }

        public EvolutionContext<T> withImplementationChooser(ImplementationChooser implementationChooser) {
            this.implementationChooser = implementationChooser;
            return this;
        }

        public EvolutionContext<T> withEvolutionResultConsumer(EvolutionResultConsumer<T> evolutionResultConsumer) {
            this.evolutionResultConsumer = evolutionResultConsumer;
            return this;
        }

        public FromEvolutionBuilder<T> from(CurrentExecutionHarness<T> currentExecutionHarness) {
            return new FromEvolutionBuilder<T>(this, currentExecutionHarness);
        }
    }
}

