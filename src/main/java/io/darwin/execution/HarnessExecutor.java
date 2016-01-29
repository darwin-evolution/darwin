package io.darwin.execution;

import io.darwin.execution.harness.ExecutionHarness;
import io.darwin.execution.result.ExecutionResult;

public class HarnessExecutor<T> {

    public ExecutionResult<T> executeHarness(ExecutionHarness<T> executionHarness) {
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
}
