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

import io.darwin.execution.ImplementationPreference;
import io.darwin.execution.result.ResultType;
import io.darwin.execution.result.evolutionary.EvolvedExecutionResult;
import io.darwin.execution.result.protoplast.ProtoplastExecutionResult;

import java.io.Serializable;

public class ComparisonResult<T> implements Serializable {

    private String name;
    private ImplementationPreference implementationPreference;
    private Object[] arguments;

    private ProtoplastExecutionResult<T> protoplastExecutionResult;
    private EvolvedExecutionResult<T> evolvedExecutionResult;

    private ResultType resultType;

    public ComparisonResult(String name, ImplementationPreference implementationPreference, Object[] arguments, ProtoplastExecutionResult<T> protoplastExecutionResult, EvolvedExecutionResult<T> evolvedExecutionResult, ResultType resultType) {
        this.name = name;
        this.implementationPreference = implementationPreference;
        this.arguments = arguments;
        this.protoplastExecutionResult = protoplastExecutionResult;
        this.evolvedExecutionResult = evolvedExecutionResult;
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

    public ProtoplastExecutionResult<T> getProtoplastExecutionResult() {
        return protoplastExecutionResult;
    }

    public EvolvedExecutionResult<T> getEvolvedExecutionResult() {
        return evolvedExecutionResult;
    }

    public ResultType getResultType() {
        return resultType;
    }
}
