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

import com.github.darwinevolution.darwin.execution.ImplementationPreference;
import com.github.darwinevolution.darwin.execution.result.ResultType;
import com.github.darwinevolution.darwin.execution.result.evolutionary.EvolvedExceptionExecutionResult;
import com.github.darwinevolution.darwin.execution.result.evolutionary.EvolvedValueExecutionResult;
import com.github.darwinevolution.darwin.execution.result.protoplast.ProtoplastExceptionExecutionResult;
import com.github.darwinevolution.darwin.execution.result.protoplast.ProtoplastValueExecutionResult;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class ResultComparatorTest {

    private static Integer[] arguments = {1, 1};

    @Test
    public void shouldCompareOkResults() {
        Integer returnValue = -6;

        ComparisonResult<Integer> shouldCompareOkResults = new ResultComparator<Integer>().compareResults("shouldCompareOkResults",
                ImplementationPreference.PROTOPLAST,
                protoplastValueExecutionResult(returnValue),
                evolvedValueExecutionResult(returnValue),
                new EqualsExceptionResultComparator(),
                new EqualsValueResultComparator<Integer>());

        Assertions.assertThat(shouldCompareOkResults.getProtoplastExecutionResult().getArguments()).isEqualTo(arguments);
        Assertions.assertThat(shouldCompareOkResults.getEvolvedExecutionResult().getArguments()).isEqualTo(arguments);
        Assertions.assertThat(shouldCompareOkResults.getImplementationPreference()).isEqualTo(ImplementationPreference.PROTOPLAST);
        Assertions.assertThat(shouldCompareOkResults.getResultType()).isEqualTo(ResultType.OK);
    }

    @Test
    public void shouldCompareOkExceptionsResult() {
        Exception exception = new IllegalArgumentException();

        ComparisonResult<Integer> shouldCompareOkResults = new ResultComparator<Integer>().compareResults("shouldCompareOkResults",
                ImplementationPreference.PROTOPLAST,
                protoplastExceptionExecutionResult(exception),
                evolvedExceptionExecutionResult(exception),
                new EqualsExceptionResultComparator(),
                new EqualsValueResultComparator<Integer>());

        Assertions.assertThat(shouldCompareOkResults.getProtoplastExecutionResult().getArguments()).isEqualTo(arguments);
        Assertions.assertThat(shouldCompareOkResults.getEvolvedExecutionResult().getArguments()).isEqualTo(arguments);
        Assertions.assertThat(shouldCompareOkResults.getImplementationPreference()).isEqualTo(ImplementationPreference.PROTOPLAST);
        Assertions.assertThat(shouldCompareOkResults.getResultType()).isEqualTo(ResultType.OK_EXCEPTIONS);
    }

    @Test
    public void shouldCompareErrorDifferentExceptionsResult() {
        Exception protoplastException = new IllegalArgumentException();
        Exception evolvedException = new IllegalAccessException();


        ComparisonResult<Integer> shouldCompareOkResults = new ResultComparator<Integer>().compareResults("shouldCompareOkResults",
                ImplementationPreference.PROTOPLAST,
                protoplastExceptionExecutionResult(protoplastException),
                evolvedExceptionExecutionResult(evolvedException),
                new EqualsExceptionResultComparator(),
                new EqualsValueResultComparator<Integer>());

        Assertions.assertThat(shouldCompareOkResults.getProtoplastExecutionResult().getArguments()).isEqualTo(arguments);
        Assertions.assertThat(shouldCompareOkResults.getEvolvedExecutionResult().getArguments()).isEqualTo(arguments);
        Assertions.assertThat(shouldCompareOkResults.getImplementationPreference()).isEqualTo(ImplementationPreference.PROTOPLAST);
        Assertions.assertThat(shouldCompareOkResults.getResultType()).isEqualTo(ResultType.ERROR_DIFFERENT_EXCEPTIONS);
    }

    @Test
    public void shouldCompareErrorDifferentResults() {
        Integer protoplastReturnValue = -6;
        Integer evolvedReturnValue = -8;

        ComparisonResult<Integer> shouldCompareOkResults = new ResultComparator<Integer>().compareResults("shouldCompareOkResults",
                ImplementationPreference.PROTOPLAST,
                protoplastValueExecutionResult(protoplastReturnValue),
                evolvedValueExecutionResult(evolvedReturnValue),
                new EqualsExceptionResultComparator(),
                new EqualsValueResultComparator<Integer>());

        Assertions.assertThat(shouldCompareOkResults.getProtoplastExecutionResult().getArguments()).isEqualTo(arguments);
        Assertions.assertThat(shouldCompareOkResults.getEvolvedExecutionResult().getArguments()).isEqualTo(arguments);
        Assertions.assertThat(shouldCompareOkResults.getImplementationPreference()).isEqualTo(ImplementationPreference.PROTOPLAST);
        Assertions.assertThat(shouldCompareOkResults.getResultType()).isEqualTo(ResultType.ERROR_DIFFERENT_RESULTS);
    }

    @Test
    public void shouldCompareErrorExceptionVsResult() {
        Integer protoplastReturnValue = -6;

        ComparisonResult<Integer> shouldCompareOkResults = new ResultComparator<Integer>().compareResults("shouldCompareOkResults",
                ImplementationPreference.PROTOPLAST,
                protoplastValueExecutionResult(protoplastReturnValue),
                evolvedExceptionExecutionResult(new IllegalArgumentException()),
                new EqualsExceptionResultComparator(),
                new EqualsValueResultComparator<Integer>());

        Assertions.assertThat(shouldCompareOkResults.getProtoplastExecutionResult().getArguments()).isEqualTo(arguments);
        Assertions.assertThat(shouldCompareOkResults.getEvolvedExecutionResult().getArguments()).isEqualTo(arguments);
        Assertions.assertThat(shouldCompareOkResults.getImplementationPreference()).isEqualTo(ImplementationPreference.PROTOPLAST);
        Assertions.assertThat(shouldCompareOkResults.getResultType()).isEqualTo(ResultType.ERROR_EXCEPTION_VS_RESULT);
    }

    @Test
    public void shouldCompareErrorExceptionVsResultForOtherWayAround() {
        Integer evolvedReturnValue = -6;

        ComparisonResult<Integer> shouldCompareOkResults = new ResultComparator<Integer>().compareResults("shouldCompareOkResults",
                ImplementationPreference.PROTOPLAST,
                protoplastExceptionExecutionResult(new IllegalArgumentException()),
                evolvedValueExecutionResult(evolvedReturnValue),
                new EqualsExceptionResultComparator(),
                new EqualsValueResultComparator<Integer>());

        Assertions.assertThat(shouldCompareOkResults.getProtoplastExecutionResult().getArguments()).isEqualTo(arguments);
        Assertions.assertThat(shouldCompareOkResults.getEvolvedExecutionResult().getArguments()).isEqualTo(arguments);
        Assertions.assertThat(shouldCompareOkResults.getImplementationPreference()).isEqualTo(ImplementationPreference.PROTOPLAST);
        Assertions.assertThat(shouldCompareOkResults.getResultType()).isEqualTo(ResultType.ERROR_EXCEPTION_VS_RESULT);
    }

    private static ProtoplastValueExecutionResult<Integer> protoplastValueExecutionResult(Integer value) {
        return new ProtoplastValueExecutionResult<Integer>(arguments, 100, value);
    }

    private static EvolvedValueExecutionResult<Integer> evolvedValueExecutionResult(Integer value) {
        return new EvolvedValueExecutionResult<Integer>(arguments,130, value);
    }

    private static ProtoplastExceptionExecutionResult<Integer> protoplastExceptionExecutionResult(Exception exception) {
        return new ProtoplastExceptionExecutionResult<Integer>(arguments, 100, exception);
    }

    private static EvolvedExceptionExecutionResult<Integer> evolvedExceptionExecutionResult(Exception exception) {
        return new EvolvedExceptionExecutionResult<Integer>(arguments, 100, exception);
    }

}
