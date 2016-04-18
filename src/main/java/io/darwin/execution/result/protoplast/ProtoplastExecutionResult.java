package io.darwin.execution.result.protoplast;

import io.darwin.evolution.protoplast.Protoplast;
import io.darwin.execution.result.ExecutionResult;

public class ProtoplastExecutionResult<T> extends ExecutionResult<T> implements Protoplast<T> {

    public ProtoplastExecutionResult(Object[] arguments, long executionTime) {
        super(arguments, executionTime);
    }
}
