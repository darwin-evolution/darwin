package io.darwin.execution.result.evolutionary;

import io.darwin.evolution.evolved.Evolved;
import io.darwin.execution.result.ExecutionResult;

public class EvolvedExecutionResult<T> extends ExecutionResult<T> implements Evolved<T> {

    public EvolvedExecutionResult(Object[] arguments, long executionTime) {
        super(arguments, executionTime);
    }
}
