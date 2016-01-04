package io.darwin.result;

import java.io.Serializable;

public class ComparisonResult<T> implements Serializable {

    private String name;
    private ImplementationPreference implementationPreference;
    private Object[] arguments;

    private T currentImplementationResult;
    private long currentImplementationExecutionTime;
    private Exception currentImplementationException;

    private T evolvedImplementationResult;
    private long evolvedImplementationExecutionTime;
    private Exception evolvedImplementationException;

    private ResultType resultType;


    public ComparisonResult(String name, ImplementationPreference implementationPreference, Object[] arguments, T currentImplementationResult, long currentImplementationExecutionTime, Exception currentImplementationException, T evolvedImplementationResult, long evolvedImplementationExecutionTime, Exception evolvedImplementationException, ResultType resultType) {
        this.name = name;
        this.implementationPreference = implementationPreference;
        this.arguments = arguments;
        this.currentImplementationResult = currentImplementationResult;
        this.currentImplementationExecutionTime = currentImplementationExecutionTime;
        this.currentImplementationException = currentImplementationException;
        this.evolvedImplementationResult = evolvedImplementationResult;
        this.evolvedImplementationExecutionTime = evolvedImplementationExecutionTime;
        this.evolvedImplementationException = evolvedImplementationException;
        this.resultType = resultType;
    }

    public String getName() {
        return name;
    }

    public ImplementationPreference getImplementationPreference() {
        return implementationPreference;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public T getCurrentImplementationResult() {
        return currentImplementationResult;
    }

    public long getCurrentImplementationExecutionTime() {
        return currentImplementationExecutionTime;
    }

    public Exception getCurrentImplementationException() {
        return currentImplementationException;
    }

    public T getEvolvedImplementationResult() {
        return evolvedImplementationResult;
    }

    public long getEvolvedImplementationExecutionTime() {
        return evolvedImplementationExecutionTime;
    }

    public Exception getEvolvedImplementationException() {
        return evolvedImplementationException;
    }

    public ResultType getResultType() {
        return resultType;
    }
}
