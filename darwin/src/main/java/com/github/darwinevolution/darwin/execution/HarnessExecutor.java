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
package com.github.darwinevolution.darwin.execution;

import com.github.darwinevolution.darwin.evolution.evolved.Evolved;
import com.github.darwinevolution.darwin.evolution.protoplast.Protoplast;
import com.github.darwinevolution.darwin.execution.harness.EvolvedExecutionHarness;
import com.github.darwinevolution.darwin.execution.result.evolutionary.EvolvedExceptionExecutionResult;
import com.github.darwinevolution.darwin.execution.result.evolutionary.EvolvedExecutionResult;
import com.github.darwinevolution.darwin.execution.result.evolutionary.EvolvedValueExecutionResult;
import com.github.darwinevolution.darwin.execution.result.protoplast.ProtoplastValueExecutionResult;
import com.github.darwinevolution.darwin.execution.harness.ExecutionHarness;
import com.github.darwinevolution.darwin.execution.harness.ProtoplastExecutionHarness;
import com.github.darwinevolution.darwin.execution.result.ExecutionResult;
import com.github.darwinevolution.darwin.execution.result.protoplast.ProtoplastExceptionExecutionResult;
import com.github.darwinevolution.darwin.execution.result.protoplast.ProtoplastExecutionResult;

/**
 * Sandbox that allows {@link Protoplast} and {@link Evolved}
 * executions harnesses to be executed and their results collected and compared.
 *
 * @param <T> result type
 */
public class HarnessExecutor<T> {

    //TODO add codehale metrics

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
