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
package io.darwin.execution;

import io.darwin.execution.harness.EvolvedExecutionHarness;
import io.darwin.execution.harness.ExecutionHarness;
import io.darwin.execution.harness.ProtoplastExecutionHarness;
import io.darwin.execution.result.ExecutionResult;
import io.darwin.execution.result.evolutionary.EvolvedExceptionExecutionResult;
import io.darwin.execution.result.evolutionary.EvolvedExecutionResult;
import io.darwin.execution.result.evolutionary.EvolvedValueExecutionResult;
import io.darwin.execution.result.protoplast.ProtoplastExceptionExecutionResult;
import io.darwin.execution.result.protoplast.ProtoplastExecutionResult;
import io.darwin.execution.result.protoplast.ProtoplastValueExecutionResult;

/**
 * Sandbox that allows {@link io.darwin.evolution.protoplast.Protoplast} and {@link io.darwin.evolution.evolved.Evolved}
 * executions harnesses to be executed and their results collected and compared.
 *
 * @param <T> result type
 */
public class HarnessExecutor<T> {

    public ProtoplastExecutionResult<T> executeProtoplastHarness(ProtoplastExecutionHarness<T> protoplastExecutionHarness) {
       return (ProtoplastExecutionResult<T>) executeHarness(protoplastExecutionHarness);
    }

    public EvolvedExecutionResult<T> executeEvolvedHarness(EvolvedExecutionHarness<T> evolvedExecutionHarness) {
       return (EvolvedExecutionResult<T>) executeHarness(evolvedExecutionHarness);
    }

    private ExecutionResult<T> executeHarness(ExecutionHarness<T> executionHarness) {
        long nanos = System.nanoTime();
        try {
            T result = executionHarness.execute();
            return createValueExecutionResult(executionHarness, nanos, result);
        } catch (Exception e) {
            return createExceptionExecutionResult(executionHarness, nanos, e);
        }
    }

    private ExecutionResult<T> createExceptionExecutionResult(ExecutionHarness<T> executionHarness, long nanoTime, Exception e) {
        if (executionHarness instanceof ProtoplastExecutionHarness) {
            return new ProtoplastExceptionExecutionResult<T>(executionHarness.getArguments(), (System.nanoTime() - nanoTime), e);
        } else {
            return new EvolvedExceptionExecutionResult<T>(executionHarness.getArguments(), (System.nanoTime() - nanoTime), e);
        }
    }

    private ExecutionResult<T> createValueExecutionResult(ExecutionHarness<T> executionHarness, long nanoTime, T result) {
        if (executionHarness instanceof ProtoplastExecutionHarness) {
            return new ProtoplastValueExecutionResult<T>(executionHarness.getArguments(), (System.nanoTime() - nanoTime), result);
        } else {
            return new EvolvedValueExecutionResult<T>(executionHarness.getArguments(), (System.nanoTime() - nanoTime), result);
        }
    }

}
