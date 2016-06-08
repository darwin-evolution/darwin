/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2016, Darwin Project
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
package com.github.darwinevolution.darwin.execution.result.comparison;

import com.github.darwinevolution.darwin.api.ExceptionResultComparator;
import com.github.darwinevolution.darwin.api.ValueResultComparator;
import com.github.darwinevolution.darwin.execution.result.evolutionary.EvolvedExecutionResult;
import com.github.darwinevolution.darwin.execution.result.protoplast.ProtoplastExecutionResult;
import com.github.darwinevolution.darwin.execution.ImplementationPreference;
import com.github.darwinevolution.darwin.execution.result.ResultType;

import java.io.Serializable;

/**
 * Class that represents result of calling {@link ResultComparator#compareResults(String, ImplementationPreference, ProtoplastExecutionResult, EvolvedExecutionResult, ExceptionResultComparator, ValueResultComparator)} <br>
 *
 * @param <T> type of result
 */
public class ComparisonResult<T> implements Serializable {

    private String name;
    private ImplementationPreference implementationPreference;

    private ProtoplastExecutionResult<T> protoplastExecutionResult;
    private EvolvedExecutionResult<T> evolvedExecutionResult;

    private ResultType resultType;

    private Long timestamp;

    public ComparisonResult(String name, ImplementationPreference implementationPreference, ProtoplastExecutionResult<T> protoplastExecutionResult, EvolvedExecutionResult<T> evolvedExecutionResult, ResultType resultType) {
        this.name = name;
        this.implementationPreference = implementationPreference;
        this.protoplastExecutionResult = protoplastExecutionResult;
        this.evolvedExecutionResult = evolvedExecutionResult;
        this.resultType = resultType;
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * @return the name of evolution which results were compared
     */
    public String getName() {
        return name;
    }

    /**
     * @return implementation preference for this evolution
     */
    public ImplementationPreference getImplementationPreference() {
        return implementationPreference;
    }

    /**
     * @return result of executing protoplast implementation
     */
    public ProtoplastExecutionResult<T> getProtoplastExecutionResult() {
        return protoplastExecutionResult;
    }

    /**
     * @return result of executing evolved implementation
     */
    public EvolvedExecutionResult<T> getEvolvedExecutionResult() {
        return evolvedExecutionResult;
    }

    /**
     * @return type of result comparison
     */
    public ResultType getResultType() {
        return resultType;
    }

    /**
     * @return timestamp of evolution execution
     */
    public Long getTimestamp() {
        return timestamp;
    }
}
