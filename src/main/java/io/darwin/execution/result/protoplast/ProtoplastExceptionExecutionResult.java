package io.darwin.execution.result.protoplast;

import io.darwin.execution.result.ExceptionExecutionResult;

public class ProtoplastExceptionExecutionResult<T> extends ProtoplastExecutionResult<T> implements ExceptionExecutionResult<T> {

    private Exception exception;

    public ProtoplastExceptionExecutionResult(Object[] arguments, long executionTime, Exception exception) {
        super(arguments, executionTime);
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProtoplastExceptionExecutionResult)) return false;
        if (!super.equals(o)) return false;

        ProtoplastExceptionExecutionResult<?> that = (ProtoplastExceptionExecutionResult<?>) o;

        return exception != null ? exception.equals(that.exception) : that.exception == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (exception != null ? exception.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProtoplastExceptionExecutionResult{" +
                "exception=" + exception +
                "} " + super.toString();
    }
}
