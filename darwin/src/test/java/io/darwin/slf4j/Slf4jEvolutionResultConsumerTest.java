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
package io.darwin.slf4j;

import io.darwin.api.ResultConsumerConfiguration;
import io.darwin.execution.ImplementationPreference;
import io.darwin.execution.result.ExceptionExecutionResult;
import io.darwin.execution.result.ResultType;
import io.darwin.execution.result.ValueExecutionResult;
import io.darwin.execution.result.comparison.ComparisonResult;
import io.darwin.execution.result.evolutionary.EvolvedExceptionExecutionResult;
import io.darwin.execution.result.evolutionary.EvolvedExecutionResult;
import io.darwin.execution.result.evolutionary.EvolvedValueExecutionResult;
import io.darwin.execution.result.protoplast.ProtoplastExceptionExecutionResult;
import io.darwin.execution.result.protoplast.ProtoplastExecutionResult;
import io.darwin.execution.result.protoplast.ProtoplastValueExecutionResult;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Arrays;
import org.junit.Test;

public class Slf4jEvolutionResultConsumerTest {

    @Test
    public void shouldCreateSilentLogMessageForValueResult(){
        Slf4jEvolutionResultConsumer<Integer> slf4jEvolutionResultConsumer = new Slf4jEvolutionResultConsumer<Integer>();

        StringBuffer loggedMessage = new StringBuffer();

        ComparisonResult<Integer> integerComparisonResult = createIntegerValueOkComparisonResult();

        slf4jEvolutionResultConsumer.consumeResults(integerComparisonResult, loggedMessage, ResultConsumerConfiguration.SILENT);

        assertConsumedSilentResult(loggedMessage, integerComparisonResult);
    }

    @Test
    public void shouldCreateVerboseLogMessageFoValueResult(){
        Slf4jEvolutionResultConsumer<Integer> slf4jEvolutionResultConsumer = new Slf4jEvolutionResultConsumer<Integer>();

        StringBuffer loggedMessage = new StringBuffer();

        ComparisonResult<Integer> integerComparisonResult = createIntegerValueOkComparisonResult();

        slf4jEvolutionResultConsumer.consumeResults(integerComparisonResult, loggedMessage, ResultConsumerConfiguration.VERBOSE);

        assertConsumedVerboseResult(loggedMessage, integerComparisonResult);
    }

    @Test
    public void shouldCreateSilentLogMessageForExceptionResult(){
        Slf4jEvolutionResultConsumer<Integer> slf4jEvolutionResultConsumer = new Slf4jEvolutionResultConsumer<Integer>();

        StringBuffer loggedMessage = new StringBuffer();

        ComparisonResult<Integer> integerComparisonResult = createIntegerOkExceptionsComparisonResult();

        slf4jEvolutionResultConsumer.consumeResults(integerComparisonResult, loggedMessage, ResultConsumerConfiguration.SILENT);

       assertConsumedSilentResult(loggedMessage, integerComparisonResult);
    }

    @Test
    public void shouldCreateVerboseLogMessageFoExceptionResult(){
        Slf4jEvolutionResultConsumer<Integer> slf4jEvolutionResultConsumer = new Slf4jEvolutionResultConsumer<Integer>();

        StringBuffer loggedMessage = new StringBuffer();

        ComparisonResult<Integer> integerComparisonResult = createIntegerOkExceptionsComparisonResult();

        slf4jEvolutionResultConsumer.consumeResults(integerComparisonResult, loggedMessage, ResultConsumerConfiguration.VERBOSE);

        assertConsumedVerboseResult(loggedMessage, integerComparisonResult);
    }

    private static ComparisonResult<Integer> createIntegerOkExceptionsComparisonResult() {
        return new ComparisonResult<Integer>("testEvolution",
                    ImplementationPreference.EVOLVED,
                    new ProtoplastExceptionExecutionResult<Integer>(Arrays.array(-1,-2), 100, new Exception("fasd")),
                    new EvolvedExceptionExecutionResult<Integer>(Arrays.array(-1,-2), 130, new Exception("fdsa")),
                    ResultType.OK_EXCEPTIONS);
    }

    private static ComparisonResult<Integer> createIntegerValueOkComparisonResult() {
        return new ComparisonResult<Integer>("testEvolution",
                ImplementationPreference.EVOLVED,
                new ProtoplastValueExecutionResult<Integer>(Arrays.array(-1,-2), 100, -6),
                new EvolvedValueExecutionResult<Integer>(Arrays.array(-1,-2), 130, -6),
                ResultType.OK);
    }

    private void assertConsumedSilentResult(StringBuffer loggedMessage, ComparisonResult comparisonResult) {
        assertCommonResult(loggedMessage, comparisonResult);

        for (Object argument : comparisonResult.getProtoplastExecutionResult().getArguments()) {
            Assertions.assertThat(loggedMessage).doesNotContain(String.valueOf(argument));
        }
        for (Object argument : comparisonResult.getEvolvedExecutionResult().getArguments()) {
            Assertions.assertThat(loggedMessage).doesNotContain(String.valueOf(argument));
        }

        ProtoplastExecutionResult protoplastExecutionResult = comparisonResult.getProtoplastExecutionResult();
        EvolvedExecutionResult evolvedExecutionResult = comparisonResult.getEvolvedExecutionResult();

        if (protoplastExecutionResult instanceof ValueExecutionResult) {
            Assertions.assertThat(loggedMessage).doesNotContain(String.valueOf(((ValueExecutionResult)protoplastExecutionResult).getValue()));
        } else {
            Assertions.assertThat(loggedMessage).doesNotContain(String.valueOf(((ExceptionExecutionResult)protoplastExecutionResult).getException().getMessage()));
        }

        if (evolvedExecutionResult instanceof ValueExecutionResult) {
            Assertions.assertThat(loggedMessage).doesNotContain(String.valueOf(((ValueExecutionResult)evolvedExecutionResult).getValue()));
        } else {
            Assertions.assertThat(loggedMessage).doesNotContain(String.valueOf(((ExceptionExecutionResult)evolvedExecutionResult).getException().getMessage()));
        }
    }

    private void assertCommonResult(StringBuffer loggedMessage, ComparisonResult comparisonResult) {
        Assertions.assertThat(loggedMessage).contains(comparisonResult.getName());
        Assertions.assertThat(loggedMessage).contains(String.valueOf(comparisonResult.getTimestamp()));
        Assertions.assertThat(loggedMessage).contains(String.valueOf(comparisonResult.getResultType().name()));
        Assertions.assertThat(loggedMessage).contains(String.valueOf(comparisonResult.getImplementationPreference().name()));
        Assertions.assertThat(loggedMessage).contains(String.valueOf(comparisonResult.getProtoplastExecutionResult().getDurationNs()));
        Assertions.assertThat(loggedMessage).contains(String.valueOf(comparisonResult.getEvolvedExecutionResult().getDurationNs()));
    }

    private void assertConsumedVerboseResult(StringBuffer loggedMessage, ComparisonResult comparisonResult) {
        assertCommonResult(loggedMessage, comparisonResult);

        for (Object argument : comparisonResult.getProtoplastExecutionResult().getArguments()) {
            Assertions.assertThat(loggedMessage).contains(String.valueOf(argument));
        }
        for (Object argument : comparisonResult.getEvolvedExecutionResult().getArguments()) {
            Assertions.assertThat(loggedMessage).contains(String.valueOf(argument));
        }

        ProtoplastExecutionResult protoplastExecutionResult = comparisonResult.getProtoplastExecutionResult();
        EvolvedExecutionResult evolvedExecutionResult = comparisonResult.getEvolvedExecutionResult();

        if (protoplastExecutionResult instanceof ValueExecutionResult) {
            Assertions.assertThat(loggedMessage).contains(String.valueOf(((ValueExecutionResult)protoplastExecutionResult).getValue()));
        } else {
            Assertions.assertThat(loggedMessage).contains(String.valueOf(((ExceptionExecutionResult)protoplastExecutionResult).getException().getMessage()));
        }

        if (evolvedExecutionResult instanceof ValueExecutionResult) {
            Assertions.assertThat(loggedMessage).contains(String.valueOf(((ValueExecutionResult)evolvedExecutionResult).getValue()));
        } else {
            Assertions.assertThat(loggedMessage).contains(String.valueOf(((ExceptionExecutionResult)evolvedExecutionResult).getException().getMessage()));
        }
    }

}
