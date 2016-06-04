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

import com.github.darwinevolution.darwin.api.*;
import com.github.darwinevolution.darwin.execution.HarnessExecutor;
import com.github.darwinevolution.darwin.execution.ImplementationPreference;
import com.github.darwinevolution.darwin.execution.harness.EvolvedExecutionHarness;
import com.github.darwinevolution.darwin.execution.harness.ProtoplastExecutionHarness;
import com.github.darwinevolution.darwin.execution.result.ExceptionExecutionResult;
import com.github.darwinevolution.darwin.execution.result.ExecutionResult;
import com.github.darwinevolution.darwin.execution.result.ValueExecutionResult;
import com.github.darwinevolution.darwin.execution.result.comparison.ComparisonResult;
import com.github.darwinevolution.darwin.execution.result.comparison.EqualsExceptionResultComparator;
import com.github.darwinevolution.darwin.execution.result.comparison.EqualsValueResultComparator;
import com.github.darwinevolution.darwin.execution.result.comparison.ResultComparator;
import com.github.darwinevolution.darwin.execution.result.evolutionary.EvolvedExecutionResult;
import com.github.darwinevolution.darwin.execution.result.protoplast.ProtoplastExecutionResult;
import com.github.darwinevolution.darwin.slf4j.Slf4jEvolutionResultConsumer;
import com.github.darwinevolution.darwin.typesafeconfig.TypesafeConfigImplementationChooser;

public class Evolution {

    /**
     * Creates evolution of type T with given name.
     *
     * @param name evolution name
     * @param <T> result type
     * @return EvolutionContext
     * @throws IllegalArgumentException - if evolution name contains illegal characters (., :, ;)
     */
    public static <T> EvolutionContext<T> of(String name) throws IllegalArgumentException {
        if (isNameValid(name)) { //branch prediction
            return new EvolutionContext<T>(name);
        } else {
            throw new IllegalArgumentException("Invalid evolution name");
        }
    }

    private static boolean isNameValid(String name) {
        return name != null
                && !name.trim().isEmpty()
                && !name.contains(".")
                && !name.contains(":")
                && !name.contains(";")
                && !name.contains("\"");
    }

    public static class FromEvolutionBuilder<T> {

        private EvolutionContext<T> evolutionContext;
        private ProtoplastExecutionHarness<T> protoplastExecutionHarness;

        public FromEvolutionBuilder(EvolutionContext<T> evolutionContext, ProtoplastExecutionHarness<T> protoplastExecutionHarness) {
            this.evolutionContext = evolutionContext;
            this.protoplastExecutionHarness = protoplastExecutionHarness;
        }

        public EvolutionBuilder<T> to(EvolvedExecutionHarness<T> evolvedExecutionHarness) {
            return new EvolutionBuilder<T>(evolutionContext, protoplastExecutionHarness, evolvedExecutionHarness);
        }
    }

    public static class EvolutionBuilder<T> {

        private EvolutionContext<T> evolutionContext;
        private ProtoplastExecutionHarness<T> protoplastExecutionHarness;
        private EvolvedExecutionHarness<T> evolvedExecutionHarness;
        private HarnessExecutor<T> harnessExecutor;
        private ResultComparator<T> resultComparator;
        private ResultConsumerConfiguration resultConsumerConfiguration = new ResultConsumerConfiguration();

        public EvolutionBuilder(EvolutionContext<T> evolutionContext, ProtoplastExecutionHarness<T> protoplastExecutionHarness, EvolvedExecutionHarness<T> evolvedExecutionHarness) {
            this.evolutionContext = evolutionContext;
            this.protoplastExecutionHarness = protoplastExecutionHarness;
            this.evolvedExecutionHarness = evolvedExecutionHarness;
            this.harnessExecutor = new HarnessExecutor<T>();
            this.resultComparator = new ResultComparator<T>();
        }

        public T evolve() throws Exception {
            ImplementationPreference implementationPreference = evolutionContext.implementationChooser.chooseImplementation(evolutionContext.name);

            ProtoplastExecutionResult<T> protoplastExecutionResult = harnessExecutor.executeProtoplastHarness(protoplastExecutionHarness);

            EvolvedExecutionResult<T> evolvedExecutionResult = harnessExecutor.executeEvolvedHarness(evolvedExecutionHarness);

            consumeResults(implementationPreference, protoplastExecutionResult, evolvedExecutionResult);

            ExecutionResult<T> preferredImplementationResult = implementationPreference.equals(ImplementationPreference.EVOLVED) ? evolvedExecutionResult : protoplastExecutionResult;

            if (preferredImplementationResult instanceof ValueExecutionResult) {
                return ((ValueExecutionResult<T>) preferredImplementationResult).getValue();
            } else {
                throw ((ExceptionExecutionResult<T>) preferredImplementationResult).getException();
            }
        }

        private void consumeResults(ImplementationPreference implementationPreference, ProtoplastExecutionResult<T> protoplastExecutionResult, EvolvedExecutionResult<T> evolvedExecutionResult) {
            ComparisonResult<T> comparisonResult = resultComparator.compareResults(evolutionContext.name, implementationPreference, protoplastExecutionResult, evolvedExecutionResult, evolutionContext.exceptionResultComparator, evolutionContext.valueResultComparator);
            evolutionContext.evolutionResultConsumer.consumeResults(comparisonResult, resultConsumerConfiguration);
        }

    }

    public static class EvolutionContext<T> {
        private String name;
        private ImplementationChooser implementationChooser;
        private EvolutionResultConsumer<T> evolutionResultConsumer;
        private ResultConsumerConfiguration resultConsumerConfiguration;
        private ValueResultComparator<T> valueResultComparator;
        private ExceptionResultComparator exceptionResultComparator;

        public EvolutionContext(String name) {
            this.name = name;
            this.implementationChooser = new TypesafeConfigImplementationChooser();
            this.evolutionResultConsumer = new Slf4jEvolutionResultConsumer<T>();
            this.resultConsumerConfiguration = new ResultConsumerConfiguration();
            this.valueResultComparator = new EqualsValueResultComparator<T>();
            this.exceptionResultComparator = new EqualsExceptionResultComparator();
        }

        public EvolutionContext<T> withImplementationChooser(ImplementationChooser implementationChooser) {
            this.implementationChooser = implementationChooser;
            return this;
        }

        public EvolutionContext<T> withEvolutionResultConsumer(EvolutionResultConsumer<T> evolutionResultConsumer) {
            this.evolutionResultConsumer = evolutionResultConsumer;
            return this;
        }

        public FromEvolutionBuilder<T> from(ProtoplastExecutionHarness<T> protoplastExecutionHarness) {
            return new FromEvolutionBuilder<T>(this, protoplastExecutionHarness);
        }

        public EvolutionContext<T> withFullDetails() {
            this.resultConsumerConfiguration = ResultConsumerConfiguration.VERBOSE;
            return this;
        }

        public EvolutionContext<T> withoutDetailsOnError() {
            this.resultConsumerConfiguration = ResultConsumerConfiguration.SILENT;
            return this;
        }

        public EvolutionContext<T> withValueResultComparator(ValueResultComparator<T> valueResultComparator) {
            this.valueResultComparator = valueResultComparator;
            return this;
        }

        public EvolutionContext<T> withExceptionResultComparator(ExceptionResultComparator exceptionResultComparator) {
            this.exceptionResultComparator = exceptionResultComparator;
            return this;
        }

        public boolean isEnabled(String name) {
            return this.implementationChooser.isEvolvedEnabled(name);
        }

        public boolean isDisabled(String name) {
            return this.implementationChooser.isEvolvedDisabled(name);
        }

    }
}

