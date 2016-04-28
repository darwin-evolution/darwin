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
import io.darwin.execution.HarnessExecutor;
import io.darwin.execution.ImplementationPreference;
import io.darwin.execution.harness.EvolvedExecutionHarness;
import io.darwin.execution.harness.ProtoplastExecutionHarness;
import io.darwin.execution.result.ExceptionExecutionResult;
import io.darwin.execution.result.ExecutionResult;
import io.darwin.execution.result.ValueExecutionResult;
import io.darwin.execution.result.comparison.ComparisonResult;
import io.darwin.execution.result.comparison.ResultComparator;
import io.darwin.execution.result.evolutionary.EvolvedExecutionResult;
import io.darwin.execution.result.protoplast.ProtoplastExecutionResult;
import io.darwin.slf4j.Slf4jEvolutionResultConsumer;
import io.darwin.typesafeconfig.TypesafeConfigImplementationChooser;

public class Evolution {

    public static <T> EvolutionContext<T> of(String name) {
        return new EvolutionContext<T>(name);
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
            ComparisonResult<T> comparisonResult = resultComparator.compareResults(evolutionContext.name, protoplastExecutionResult.getArguments(), implementationPreference, protoplastExecutionResult, evolvedExecutionResult);
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

        public FromEvolutionBuilder<T> from(ProtoplastExecutionHarness<T> protoplastExecutionHarness) {
            return new FromEvolutionBuilder<T>(this, protoplastExecutionHarness);
        }

        public boolean isEnabled(String name) {
            return this.implementationChooser.isEvolvedEnabled(name);
        }

        public boolean isDisabled(String name) {
            return this.implementationChooser.isEvolvedDisabled(name);
        }

    }
}

