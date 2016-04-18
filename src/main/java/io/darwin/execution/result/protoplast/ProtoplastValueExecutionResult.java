package io.darwin.execution.result.protoplast;

import io.darwin.execution.result.ValueExecutionResult;

public class ProtoplastValueExecutionResult<T> extends ProtoplastExecutionResult<T> implements ValueExecutionResult<T> {

    private T value;

    public ProtoplastValueExecutionResult(Object[] arguments, long executionTime, T value) {
        super(arguments, executionTime);
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProtoplastValueExecutionResult)) return false;
        if (!super.equals(o)) return false;

        ProtoplastValueExecutionResult<?> that = (ProtoplastValueExecutionResult<?>) o;

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
        return "ProtoplastValueExecutionResult{" +
                "value=" + value +
                "} " + super.toString();
    }
}
