package io.darwin.execution.result.evolutionary;

import io.darwin.execution.result.ExceptionExecutionResult;

public class EvolvedExceptionExecutionResult<T> extends EvolvedExecutionResult<T> implements ExceptionExecutionResult<T> {

    private Exception exception;

    public EvolvedExceptionExecutionResult(Object[] arguments, long executionTime, Exception exception) {
        super(arguments, executionTime);
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EvolvedExceptionExecutionResult)) return false;
        if (!super.equals(o)) return false;

        EvolvedExceptionExecutionResult<?> that = (EvolvedExceptionExecutionResult<?>) o;

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
        return "EvolvedExceptionExecutionResult{" +
                "exception=" + exception +
                "} " + super.toString();
    }
}
