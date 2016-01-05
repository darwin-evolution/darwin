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

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.darwin.harness.CurrentExecutionHarness;
import io.darwin.harness.EvolvedExecutionHarness;
import io.darwin.harness.ExecutionHarness;
import io.darwin.result.ComparisonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Evolution {

    public static final String CONFIG_PATH_PREFIX = "darwin.evolution";
    public static final String CONFIG_PATH_SUFFIX = "evolved";

    public static <T> EvolutionContext<T> of(String name) {
        return new EvolutionContext<T>(name);
    }

    public static AsyncEvolutionBuilder.EvolutionTimeout ms(Long evolutionTimeout) {
        return new AsyncEvolutionBuilder.EvolutionTimeout(evolutionTimeout, TimeUnit.MILLISECONDS);
    }

    public static class FromEvolutionBuilder<T> {

        private String name;
        private CurrentExecutionHarness<T> currentExecutionHarness;

        public FromEvolutionBuilder(String name, CurrentExecutionHarness<T> currentExecutionHarness) {
            this.name = name;
            this.currentExecutionHarness = currentExecutionHarness;
        }

        public EvolutionBuilder<T> to(EvolvedExecutionHarness<T> evolvedExecutionHarness) {
            return new EvolutionBuilder<T>(name, currentExecutionHarness, evolvedExecutionHarness);
        }
    }

    public static class EvolutionBuilder<T> {
        private static final Logger log = LoggerFactory.getLogger("darwin.evolution");

        private String name;
        private CurrentExecutionHarness<T> currentExecutionHarness;
        private EvolvedExecutionHarness<T> evolvedExecutionHarness;

        public EvolutionBuilder(String name, CurrentExecutionHarness<T> currentExecutionHarness, EvolvedExecutionHarness<T> evolvedExecutionHarness) {
            this.name = name;
            this.currentExecutionHarness = currentExecutionHarness;
            this.evolvedExecutionHarness = evolvedExecutionHarness;
        }

//        public AsyncEvolutionBuilder<T> async() {
//            return new AsyncEvolutionBuilder<T>(this);
//        }


        public T evolve() throws Exception {
            boolean evolvedImplementationPreferred =  getConfiguredImplementationPreference();

            ExecutionResult<T> currentExecutionResult = executeHarness(currentExecutionHarness);

            ExecutionResult<T> evolvedExecutionResult = executeHarness(evolvedExecutionHarness);

            compareResults(evolvedImplementationPreferred, currentExecutionResult, evolvedExecutionResult);

            ExecutionResult<T> preferredImplementationResult = evolvedImplementationPreferred ? evolvedExecutionResult : currentExecutionResult;

            if (preferredImplementationResult.exception == null) {
                return preferredImplementationResult.result;
            } else {
                throw preferredImplementationResult.exception;
            }
        }

        private boolean getConfiguredImplementationPreference() {
            Config config = ConfigFactory.load();
            String evolvedImplementationEnabled = CONFIG_PATH_PREFIX + "." + name + "." + CONFIG_PATH_SUFFIX;
            return config.hasPath(evolvedImplementationEnabled) && config.getBoolean(evolvedImplementationEnabled);
        }

        private ComparisonResult compareResults(boolean evolvedImplementationPreferred, ExecutionResult<T> currentExecutionResult, ExecutionResult<T> evolvedExecutionResult) {


            String comparisonResult = "ERROR";

            StringBuffer sb = new StringBuffer();

            sb.append(name).append("|");
            sb.append(evolvedImplementationPreferred ? "EVOLVED" : "CURRENT").append("|");
            sb.append(currentExecutionResult.result).append("|");
            sb.append(currentExecutionResult.executionTime).append("|");
            sb.append(currentExecutionResult.exception).append("|");
            sb.append(evolvedExecutionResult.result).append("|");
            sb.append(evolvedExecutionResult.executionTime).append("|");
            sb.append(evolvedExecutionResult.exception).append("|");

            System.out.println(sb.toString());

            if (currentExecutionResult.exception != null) {
                if (Objects.equals(currentExecutionResult.exception, evolvedExecutionResult.exception)) {

                }
            }
            return null;
         }

        private ExecutionResult<T> getConfiguredExecutionResult(ExecutionResult<T> currentExecutionResult, ExecutionResult<T> evolvedExecutionResult) {
            Config config = ConfigFactory.load();
            String evolvedImplementationEnabled = CONFIG_PATH_PREFIX + "." + name + "." + CONFIG_PATH_SUFFIX;
            if (config.hasPath(evolvedImplementationEnabled) && config.getBoolean(evolvedImplementationEnabled)) {
                return evolvedExecutionResult;
            } else {
                return currentExecutionResult;
            }
        }


        private ExecutionResult<T> executeHarness(ExecutionHarness<T> executionHarness) {
            T result = null;
            Exception exception = null;
            long executionDuration = -1L;

            try {
                long executionStartMillis = System.currentTimeMillis();
                result = executionHarness.execute();
                executionDuration = System.currentTimeMillis() - executionStartMillis;
            } catch (Exception e) {
                exception = e;
            }

            return new ExecutionResult<T>(executionHarness.getArguments(), result, executionDuration, exception);
        }


        private static class ExecutionResult<T> {
            T result;
            long executionTime;
            Exception exception;
            Object[] arguments;

            public ExecutionResult(Object[] arguments, T result, long executionTime, Exception exception) {
                this.arguments = arguments;
                this.result = result;
                this.executionTime = executionTime;
                this.exception = exception;
            }

            public T getResult() {
                return result;
            }

            public long getExecutionTime() {
                return executionTime;
            }

            public Exception getException() {
                return exception;
            }

            public Object[] getArguments() {
                return arguments;
            }
        }
    }

    public static class EvolutionContext<T> {
        private String name;

        public EvolutionContext(String name) {
            this.name = name;
        }

        public FromEvolutionBuilder<T> from(CurrentExecutionHarness<T> currentExecutionHarness) {
            return new FromEvolutionBuilder<T>(name, currentExecutionHarness);
        }
    }
}
