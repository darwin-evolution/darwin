/**
 * The MIT License
 *
 * Copyright (c) 2016 Darwin contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
