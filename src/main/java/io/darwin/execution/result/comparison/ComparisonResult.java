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
package io.darwin.execution.result.comparison;

import io.darwin.execution.ImplementationPreferenceType;
import io.darwin.execution.result.ResultType;

import java.io.Serializable;

public class ComparisonResult<T> implements Serializable {

    private String name;
    private ImplementationPreferenceType implementationPreferenceType;
    private Object[] arguments;

    private T currentImplementationResult;
    private long currentImplementationExecutionTime;
    private Exception currentImplementationException;

    private T evolvedImplementationResult;
    private long evolvedImplementationExecutionTime;
    private Exception evolvedImplementationException;

    private ResultType resultType;


    public ComparisonResult(String name, ImplementationPreferenceType implementationPreferenceType, Object[] arguments, T currentImplementationResult, long currentImplementationExecutionTime, Exception currentImplementationException, T evolvedImplementationResult, long evolvedImplementationExecutionTime, Exception evolvedImplementationException, ResultType resultType) {
        this.name = name;
        this.implementationPreferenceType = implementationPreferenceType;
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

    public ImplementationPreferenceType getImplementationPreferenceType() {
        return implementationPreferenceType;
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
