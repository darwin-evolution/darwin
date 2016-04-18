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
import io.darwin.execution.result.ExecutionResult;
import io.darwin.execution.result.ResultType;
import org.testng.annotations.Test;


public class IntegerResultComparatorTest {

    private Integer[] arguments = {1, 1};

    @Test
    public void shouldCompareOkResults() {

//        ComparisonResult<Integer> shouldCompareOkResults = new ResultComparator<Integer>().compareResults("shouldCompareOkResults",
//                arguments,
//                ImplementationPreference.PROTOPLAST,
//                firstExecutionResultWithoutException(),
//                firstExecutionResultWithoutException());
//
//        assertThat(shouldCompareOkResults.getArguments()).isEqualTo(arguments);
//
//        assertThat(shouldCompareOkResults.getCurrentImplementationException()).isNull();
//        assertThat(shouldCompareOkResults.getCurrentImplementationResult()).isEqualTo(1);
//
//        assertThat(shouldCompareOkResults.getEvolvedImplementationException()).isNull();
//        assertThat(shouldCompareOkResults.getEvolvedImplementationResult()).isEqualTo(1);
//
//        assertThat(shouldCompareOkResults.getImplementationPreference()).isEqualTo(ImplementationPreference.PROTOPLAST);
//        assertThat(shouldCompareOkResults.getResultType()).isEqualTo(ResultType.OK);
//    }
//
//    @Test
//    public void shouldCompareOkResultsForTwoNulls() {
//
//        ComparisonResult<Integer> shouldCompareOkResults = new ResultComparator<Integer>().compareResults("shouldCompareOkResultsForTwoNulls",
//                arguments,
//                ImplementationPreference.PROTOPLAST,
//                nullExecutionResultWithoutException(),
//                nullExecutionResultWithoutException());
//
//        assertThat(shouldCompareOkResults.getArguments()).isEqualTo(arguments);
//
//        assertThat(shouldCompareOkResults.getCurrentImplementationException()).isNull();
//        assertThat(shouldCompareOkResults.getCurrentImplementationResult()).isNull();
//
//        assertThat(shouldCompareOkResults.getEvolvedImplementationException()).isNull();
//        assertThat(shouldCompareOkResults.getEvolvedImplementationResult()).isNull();
//
//        assertThat(shouldCompareOkResults.getImplementationPreference()).isEqualTo(ImplementationPreference.PROTOPLAST);
//        assertThat(shouldCompareOkResults.getResultType()).isEqualTo(ResultType.OK);
//    }
//
//    @Test
//    public void shouldCompareOkExceptionsResult() {
//
//        ComparisonResult<Integer> shouldCompareOkResults = new ResultComparator<Integer>().compareResults("shouldCompareOkExceptionsResult",
//                arguments,
//                ImplementationPreference.PROTOPLAST,
//                executionResultWithException(),
//                executionResultWithException());
//
//        assertThat(shouldCompareOkResults.getArguments()).isEqualTo(arguments);
//
//        assertThat(shouldCompareOkResults.getCurrentImplementationException()).isNotNull();
//        assertThat(shouldCompareOkResults.getCurrentImplementationResult()).isNull();
//
//        assertThat(shouldCompareOkResults.getEvolvedImplementationException()).isNotNull();
//        assertThat(shouldCompareOkResults.getEvolvedImplementationResult()).isNull();
//
//        assertThat(shouldCompareOkResults.getImplementationPreference()).isEqualTo(ImplementationPreference.PROTOPLAST);
//        assertThat(shouldCompareOkResults.getResultType()).isEqualTo(ResultType.OK_EXCEPTIONS);
//    }
//
//    @Test
//    public void shouldCompareOkExceptionsResultForDifferentStackTraces() {
//
//        ComparisonResult<Integer> shouldCompareOkResults = new ResultComparator<Integer>().compareResults("shouldCompareOkExceptionsResultForDifferentStackTraces",
//                arguments,
//                ImplementationPreference.PROTOPLAST,
//                executionResultWithException(),
//                secondExecutionResultWithException());
//
//        assertThat(shouldCompareOkResults.getArguments()).isEqualTo(arguments);
//
//        assertThat(shouldCompareOkResults.getCurrentImplementationException()).isNotNull();
//        assertThat(shouldCompareOkResults.getCurrentImplementationResult()).isNull();
//
//        assertThat(shouldCompareOkResults.getEvolvedImplementationException()).isNotNull();
//        assertThat(shouldCompareOkResults.getEvolvedImplementationResult()).isNull();
//
//        assertThat(shouldCompareOkResults.getImplementationPreference()).isEqualTo(ImplementationPreference.PROTOPLAST);
//        assertThat(shouldCompareOkResults.getResultType()).isEqualTo(ResultType.OK_EXCEPTIONS);
//    }
//
//    @Test
//    public void shouldCompareErrorDifferentResults() {
//
//        ComparisonResult<Integer> shouldCompareOkResults = new ResultComparator<Integer>().compareResults("shouldCompareErrorDifferentResults",
//                arguments,
//                ImplementationPreference.PROTOPLAST,
//                firstExecutionResultWithoutException(),
//                secondExecutionResultWithoutException());
//
//        assertThat(shouldCompareOkResults.getArguments()).isEqualTo(arguments);
//
//        assertThat(shouldCompareOkResults.getCurrentImplementationException()).isNull();
//        assertThat(shouldCompareOkResults.getCurrentImplementationResult()).isEqualTo(1);
//
//        assertThat(shouldCompareOkResults.getEvolvedImplementationException()).isNull();
//        assertThat(shouldCompareOkResults.getEvolvedImplementationResult()).isEqualTo(2);
//
//        assertThat(shouldCompareOkResults.getImplementationPreference()).isEqualTo(ImplementationPreference.PROTOPLAST);
//        assertThat(shouldCompareOkResults.getResultType()).isEqualTo(ResultType.ERROR_DIFFERENT_RESULTS);
//    }
//
//    @Test
//    public void shouldCompareErrorDifferentResultsForNullAndNotNull() {
//
//        ComparisonResult<Integer> shouldCompareOkResults = new ResultComparator<Integer>().compareResults("shouldCompareErrorDifferentResultsForNullAndNotNull",
//                arguments,
//                ImplementationPreference.PROTOPLAST,
//                firstExecutionResultWithoutException(),
//                nullExecutionResultWithoutException());
//
//        assertThat(shouldCompareOkResults.getArguments()).isEqualTo(arguments);
//
//        assertThat(shouldCompareOkResults.getCurrentImplementationException()).isNull();
//        assertThat(shouldCompareOkResults.getCurrentImplementationResult()).isEqualTo(1);
//
//        assertThat(shouldCompareOkResults.getEvolvedImplementationException()).isNull();
//        assertThat(shouldCompareOkResults.getEvolvedImplementationResult()).isNull();
//
//        assertThat(shouldCompareOkResults.getImplementationPreference()).isEqualTo(ImplementationPreference.PROTOPLAST);
//        assertThat(shouldCompareOkResults.getResultType()).isEqualTo(ResultType.ERROR_DIFFERENT_RESULTS);
//    }
//
//    @Test
//    public void shouldCompareErrorExceptionVsResult() {
//
//        ComparisonResult<Integer> shouldCompareOkResults = new ResultComparator<Integer>().compareResults("shouldCompareErrorExceptionVsResult",
//                arguments,
//                ImplementationPreference.PROTOPLAST,
//                firstExecutionResultWithoutException(),
//                executionResultWithException());
//
//        assertThat(shouldCompareOkResults.getArguments()).isEqualTo(arguments);
//
//        assertThat(shouldCompareOkResults.getCurrentImplementationException()).isNull();
//        assertThat(shouldCompareOkResults.getCurrentImplementationResult()).isEqualTo(1);
//
//        assertThat(shouldCompareOkResults.getEvolvedImplementationException()).isNotNull();
//        assertThat(shouldCompareOkResults.getEvolvedImplementationResult()).isNull();
//
//        assertThat(shouldCompareOkResults.getImplementationPreference()).isEqualTo(ImplementationPreference.PROTOPLAST);
//        assertThat(shouldCompareOkResults.getResultType()).isEqualTo(ResultType.ERROR_EXCEPTION_VS_RESULT);
//    }
//
//    @Test
//    public void shouldCompareErrorExceptionVsResultForOtherWayAround() {
//
//        ComparisonResult<Integer> shouldCompareOkResults = new ResultComparator<Integer>().compareResults("shouldCompareErrorExceptionVsResultForOtherWayAround",
//                arguments,
//                ImplementationPreference.PROTOPLAST,
//                executionResultWithException(),
//                firstExecutionResultWithoutException());
//
//        assertThat(shouldCompareOkResults.getArguments()).isEqualTo(arguments);
//
//        assertThat(shouldCompareOkResults.getCurrentImplementationException()).isNotNull();
//        assertThat(shouldCompareOkResults.getCurrentImplementationResult()).isNull();
//
//        assertThat(shouldCompareOkResults.getEvolvedImplementationException()).isNull();
//        assertThat(shouldCompareOkResults.getEvolvedImplementationResult()).isEqualTo(1);
//
//        assertThat(shouldCompareOkResults.getImplementationPreference()).isEqualTo(ImplementationPreference.PROTOPLAST);
//        assertThat(shouldCompareOkResults.getResultType()).isEqualTo(ResultType.ERROR_EXCEPTION_VS_RESULT);
//    }
//
//
//
//    private ExecutionResult<Integer> firstExecutionResultWithoutException() {
//        return new ExecutionResult<Integer>(arguments, 1, 0, null);
//    }
//
//    private ExecutionResult<Integer> secondExecutionResultWithoutException() {
//
//        return new ExecutionResult<Integer>(arguments, 2, 0, null);
//    }
//
//    private ExecutionResult<Integer> nullExecutionResultWithoutException() {
//
//        return new ExecutionResult<Integer>(arguments, null, 0, null);
//    }
//
//    private ExecutionResult<Integer> executionResultWithException() {
//        return new ExecutionResult<Integer>(arguments, null, 0, new IllegalAccessException());
//    }
//
//    private ExecutionResult<Integer> secondExecutionResultWithException() {
//        return new ExecutionResult<Integer>(arguments, null, 0, new IllegalAccessException());
//    }

    }
}
