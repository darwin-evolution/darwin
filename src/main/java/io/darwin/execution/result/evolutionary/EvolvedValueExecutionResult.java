package io.darwin.execution.result.evolutionary;

import io.darwin.execution.result.ValueExecutionResult;

public class EvolvedValueExecutionResult<T> extends EvolvedExecutionResult<T> implements ValueExecutionResult<T> {

    private T value;

    public EvolvedValueExecutionResult(Object[] arguments, long executionTime, T value) {
        super(arguments, executionTime);
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EvolvedValueExecutionResult)) return false;
        if (!super.equals(o)) return false;

        EvolvedValueExecutionResult<?> that = (EvolvedValueExecutionResult<?>) o;

        return value != null ? value.equals(that.value) : that.value == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EvolvedValueExecutionResult{" +
                "value=" + value +
                "} " + super.toString();
    }
}
