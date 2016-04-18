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
import io.darwin.execution.result.ExceptionExecutionResult;
import io.darwin.execution.result.ResultType;
import io.darwin.execution.result.ValueExecutionResult;
import io.darwin.execution.result.evolutionary.EvolvedExceptionExecutionResult;
import io.darwin.execution.result.evolutionary.EvolvedExecutionResult;
import io.darwin.execution.result.protoplast.ProtoplastExceptionExecutionResult;
import io.darwin.execution.result.protoplast.ProtoplastExecutionResult;

import java.util.Objects;

public class ResultComparator<T> {

    public ComparisonResult<T> compareResults(String evolutionName, Object[] arguments, ImplementationPreference implementationPreference, ProtoplastExecutionResult<T> protoplastExecutionResult, EvolvedExecutionResult<T> evolvedExecutionResult) {
        ResultType resultType = ResultType.INVALID_STATE;

        if (protoplastExecutionResult instanceof ValueExecutionResult) {
            if (evolvedExecutionResult instanceof ValueExecutionResult) {
                if (Objects.equals(((ValueExecutionResult) protoplastExecutionResult).getValue(), ((ValueExecutionResult) evolvedExecutionResult).getValue())) {
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
            } else if (areExceptionsEqual((ProtoplastExceptionExecutionResult<T>) protoplastExecutionResult, (EvolvedExceptionExecutionResult<T>) evolvedExecutionResult)) {
                resultType = ResultType.OK_EXCEPTIONS;
            }  else {
                resultType = ResultType.ERROR_DIFFERENT_EXCEPTIONS;
            }
        }

        return new ComparisonResult<T>(evolutionName, implementationPreference, arguments,  protoplastExecutionResult, evolvedExecutionResult, resultType);
    }

    private boolean areExceptionsEqual(ProtoplastExceptionExecutionResult<T> currentExecutionResult, EvolvedExceptionExecutionResult<T> evolvedExecutionResult) {
        return Objects.equals(currentExecutionResult.getException().getClass(), evolvedExecutionResult.getException().getClass());
    }
}
