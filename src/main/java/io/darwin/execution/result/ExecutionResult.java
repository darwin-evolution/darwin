package io.darwin.execution.result;

import java.io.Serializable;
import java.util.Arrays;

public class ExecutionResult<T> implements Serializable {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExecutionResult)) return false;

        ExecutionResult<?> that = (ExecutionResult<?>) o;

        if (executionTime != that.executionTime) return false;
        if (result != null ? !result.equals(that.result) : that.result != null) return false;
        if (exception != null ? !exception.equals(that.exception) : that.exception != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(arguments, that.arguments);

    }

    @Override
    public int hashCode() {
        int result1 = result != null ? result.hashCode() : 0;
        result1 = 31 * result1 + (int) (executionTime ^ (executionTime >>> 32));
        result1 = 31 * result1 + (exception != null ? exception.hashCode() : 0);
        result1 = 31 * result1 + Arrays.hashCode(arguments);
        return result1;
    }

    @Override
    public String toString() {
        return "ExecutionResult{" +
                "result=" + result +
                ", executionTime=" + executionTime +
                ", exception=" + exception +
                ", arguments=" + Arrays.toString(arguments) +
                '}';
    }
}
