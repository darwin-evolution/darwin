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
import com.github.darwinevolution.darwin.execution.ImplementationPreference;
import com.github.darwinevolution.darwin.execution.result.ExceptionExecutionResult;
import com.github.darwinevolution.darwin.execution.result.ResultType;
import com.github.darwinevolution.darwin.execution.result.ValueExecutionResult;
import com.github.darwinevolution.darwin.execution.result.evolutionary.EvolvedExecutionResult;
import com.github.darwinevolution.darwin.execution.result.protoplast.ProtoplastExecutionResult;

public class ResultComparator<T> {

    public ComparisonResult<T> compareResults(String evolutionName, ImplementationPreference implementationPreference, ProtoplastExecutionResult<T> protoplastExecutionResult, EvolvedExecutionResult<T> evolvedExecutionResult, ExceptionResultComparator exceptionResultComparator, ValueResultComparator<T> valueResultComparator) {
        ResultType resultType = ResultType.INVALID_STATE;

        if (protoplastExecutionResult instanceof ValueExecutionResult) {
            if (evolvedExecutionResult instanceof ValueExecutionResult) {
                if (valueResultComparator.areValuesEqual(((ValueExecutionResult<T>) protoplastExecutionResult).getValue(), ((ValueExecutionResult<T>) evolvedExecutionResult).getValue())){
                    resultType = ResultType.OK;
                } else {
                    resultType = ResultType.ERROR_DIFFERENT_RESULTS;
                }
            } else {
                resultType = ResultType.ERROR_EXCEPTION_VS_RESULT;
            }
        } else if(protoplastExecutionResult instanceof ExceptionExecutionResult) {
            if (evolvedExecutionResult instanceof ValueExecutionResult) {
                resultType = ResultType.ERROR_EXCEPTION_VS_RESULT;
            } else if (exceptionResultComparator.areExceptionsEqual(((ExceptionExecutionResult) protoplastExecutionResult).getException(), ((ExceptionExecutionResult)evolvedExecutionResult).getException())) {
                resultType = ResultType.OK_EXCEPTIONS;
            }  else {
                resultType = ResultType.ERROR_DIFFERENT_EXCEPTIONS;
            }
        }

        return new ComparisonResult<T>(evolutionName, implementationPreference, protoplastExecutionResult, evolvedExecutionResult, resultType);
    }

}
