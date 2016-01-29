package io.darwin.api;

import io.darwin.execution.result.ExecutionResult;
import io.darwin.execution.result.comparison.ComparisonResult;
import io.darwin.execution.ImplementationPreferenceType;

import java.util.Objects;

public abstract class EvolutionResultConsumer<T> {

    public abstract void consumeResults(String evolutionName, ImplementationPreferenceType implementationPreferenceType, ExecutionResult<T> currentExecutionResult, ExecutionResult<T> evolvedExecutionResult);

    protected ComparisonResult<T> compareResults(ImplementationPreferenceType implementationPreferenceType, ExecutionResult<T> currentExecutionResult, ExecutionResult<T> evolvedExecutionResult) {
        //TODO implement result comparsion algorithm

        if (currentExecutionResult.getException() != null) {
            if (Objects.equals(currentExecutionResult.getException(), evolvedExecutionResult.getException())) {

            }
        }
            return null;
    }
}
