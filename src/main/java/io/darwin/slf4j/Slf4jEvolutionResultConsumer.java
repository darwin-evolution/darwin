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

import io.darwin.api.EvolutionResultConsumer;
import io.darwin.execution.result.ExceptionExecutionResult;
import io.darwin.execution.result.ExecutionResult;
import io.darwin.execution.result.ValueExecutionResult;
import io.darwin.execution.result.comparison.ComparisonResult;
import io.darwin.execution.result.evolutionary.EvolvedExecutionResult;
import io.darwin.execution.result.protoplast.ProtoplastExecutionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class Slf4jEvolutionResultConsumer<T> extends EvolutionResultConsumer<T> {

    private static final Logger log = LoggerFactory.getLogger("darwin.evolution");

    @Override
    public void consumeResults(ComparisonResult<T> comparisonResult) {
        StringBuffer sb = new StringBuffer();

        sb = consumeResults(comparisonResult, sb);

        log.info(sb.toString());
    }

    protected StringBuffer consumeResults(ComparisonResult<T> comparisonResult, StringBuffer stringBuffer) {

        stringBuffer = appendEvolutionName(comparisonResult, stringBuffer);

        stringBuffer = appendExecutionArguments(comparisonResult, stringBuffer);

        stringBuffer = appendImplementationPreference(comparisonResult, stringBuffer);

        stringBuffer = appendProtoplastExecutionResult(comparisonResult.getProtoplastExecutionResult(), stringBuffer);

        stringBuffer = appendEvolvedExecutionResult(comparisonResult.getEvolvedExecutionResult(), stringBuffer);

        stringBuffer = appendResultType(comparisonResult, stringBuffer);

        return stringBuffer;
    }

    private StringBuffer appendEvolvedExecutionResult(EvolvedExecutionResult<T> evolvedExecutionResult, StringBuffer sb) {
        appendExecutionResult(evolvedExecutionResult, sb);
        return sb;
    }

    private StringBuffer appendProtoplastExecutionResult(ProtoplastExecutionResult<T> protoplastExecutionResult, StringBuffer sb) {
        appendExecutionResult(protoplastExecutionResult, sb);
        return sb;
    }

    private StringBuffer appendExecutionResult(ExecutionResult<T> executionResult, StringBuffer sb) {
        sb.append(executionResult.getDurationNs()).append("|");
        if (executionResult instanceof ExceptionExecutionResult) {
            sb.append("").append("|");
            sb.append(((ExceptionExecutionResult)executionResult).getException()).append("|");

        } else if (executionResult instanceof ValueExecutionResult) {
            sb.append(((ValueExecutionResult)executionResult).getValue()).append("|");
            sb.append("").append("|");
        }
        return sb;
    }

    private StringBuffer appendResultType(ComparisonResult<T> comparisonResult, StringBuffer sb) {
        return sb.append(comparisonResult.getResultType().name());
    }

    private StringBuffer appendImplementationPreference(ComparisonResult<T> comparisonResult, StringBuffer sb) {
        return sb.append(comparisonResult.getImplementationPreference().name()).append("|");
    }

    private StringBuffer appendExecutionArguments(ComparisonResult<T> comparisonResult, StringBuffer sb) {
        return sb.append(Arrays.toString(comparisonResult.getArguments()).replaceAll("\\|", "\\\\|")).append("|");
    }

    private StringBuffer appendEvolutionName(ComparisonResult<T> comparisonResult, StringBuffer sb) {
        return sb.append(comparisonResult.getName()).append("|");
    }
}
