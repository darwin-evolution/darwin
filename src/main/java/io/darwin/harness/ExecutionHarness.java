package io.darwin.harness;

public abstract class ExecutionHarness<T> {

    private  Object[] arguments = null;

    protected void arguments(Object ...arguments) {
        this.arguments = arguments;
    }

    public Object[] getArguments() {
        return this.arguments;
    }

    public abstract T execute() throws Exception;

}
