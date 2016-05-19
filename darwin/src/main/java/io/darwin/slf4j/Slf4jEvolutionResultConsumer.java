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
import io.darwin.api.ResultConsumerConfiguration;
import io.darwin.execution.result.ExceptionExecutionResult;
import io.darwin.execution.result.ExecutionResult;
import io.darwin.execution.result.ResultType;
import io.darwin.execution.result.ValueExecutionResult;
import io.darwin.execution.result.comparison.ComparisonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jEvolutionResultConsumer<T> extends EvolutionResultConsumer<T> {

    private static final Logger evolutionLog = LoggerFactory.getLogger("darwin.evolution");
    private static final Logger log = LoggerFactory.getLogger(Slf4jEvolutionResultConsumer.class);

    private static final String VALUE_SEPARATOR = "|";
    private static final String ERROR_TOKEN = "ERR_GENERATING";
    private static final String QUOTE = "\"";

    @Override
    public void consumeResults(ComparisonResult<T> comparisonResult, ResultConsumerConfiguration resultConsumerConfiguration) {
        StringBuffer sb = new StringBuffer();

        sb = consumeResults(comparisonResult, sb, resultConsumerConfiguration);

        evolutionLog.info(sb.toString());
    }

    protected StringBuffer consumeResults(ComparisonResult<T> comparisonResult, StringBuffer stringBuffer, ResultConsumerConfiguration resultConsumerConfiguration) {

        appendTextValue(comparisonResult.getName(), stringBuffer);

        appendTextValue(comparisonResult.getImplementationPreference().name(), stringBuffer);

        appendTextValue(String.valueOf(comparisonResult.getTimestamp()), stringBuffer);

        appendTextValue(comparisonResult.getResultType().name(), stringBuffer);

        stringBuffer = appendExecutionResult(comparisonResult.getProtoplastExecutionResult(), stringBuffer, resultConsumerConfiguration, comparisonResult.getResultType());

        stringBuffer = appendExecutionResult(comparisonResult.getEvolvedExecutionResult(), stringBuffer, resultConsumerConfiguration, comparisonResult.getResultType());

        return stringBuffer;
    }

    private StringBuffer appendExecutionResult(ExecutionResult<T> executionResult, StringBuffer sb, ResultConsumerConfiguration resultConsumerConfiguration, ResultType resultType) {
        appendArguments(executionResult, sb, resultConsumerConfiguration, resultType);

        appendTextValue(String.valueOf(executionResult.getDurationNs()), sb);

        appendResult(executionResult, sb, resultConsumerConfiguration, resultType);

        return sb;
    }

    private void appendResult(ExecutionResult<T> executionResult, StringBuffer sb, ResultConsumerConfiguration resultConsumerConfiguration, ResultType resultType) {
        if (resultConsumerConfiguration.shouldPrintResults()
                || shouldPrintDetails(resultType, resultConsumerConfiguration)) {

            if (executionResult instanceof ExceptionExecutionResult) {

                sb.append(VALUE_SEPARATOR);
                appendExceptionResult((ExceptionExecutionResult) executionResult, sb);

            } else if (executionResult instanceof ValueExecutionResult) {

                appendValueResult((ValueExecutionResult) executionResult, sb);
                sb.append(VALUE_SEPARATOR);

            }

        } else {
            sb.append(VALUE_SEPARATOR);
            sb.append(VALUE_SEPARATOR);
        }
    }

    private void appendArguments(ExecutionResult<T> executionResult, StringBuffer sb, ResultConsumerConfiguration resultConsumerConfiguration, ResultType resultType) {
        if (resultConsumerConfiguration.shouldPrintBothArguments()
                || shouldPrintDetails(resultType, resultConsumerConfiguration)) {
            sb.append(QUOTE).append("[");
            for (Object argument : executionResult.getArguments()) {
                sb.append(escapeQuotes(argument)).append(",");
            }
            removeLastChar(sb);
            sb.append("]").append(QUOTE);
        }
        sb.append(VALUE_SEPARATOR);
    }

    private static void removeLastChar(StringBuffer sb) {
        sb.deleteCharAt(sb.length()-1);
    }

    private static void appendValueResult(ValueExecutionResult executionResult, StringBuffer sb) {
        try {
            appendTextValue(executionResult.getValue(), sb);
        } catch (Exception e) {
            log.error("Error while generating string from exception", e);
            appendErrorToken(sb);
        }
    }

    private static void appendExceptionResult(ExceptionExecutionResult executionResult, StringBuffer sb) {
        try {
            appendTextValue(executionResult.getException(), sb);
        } catch (Exception e) {
            log.error("Error while generating string from value", e);
            appendErrorToken(sb);
        }
    }

    private static void appendErrorToken(StringBuffer sb) {
        appendTextValue(ERROR_TOKEN, sb);
    }

    private static boolean shouldPrintDetails(ResultType resultType, ResultConsumerConfiguration resultConsumerConfiguration) {
        return resultType.isError() && resultConsumerConfiguration.shouldPrintDetailsOnError();
    }

    private static void appendTextValue(Object value, StringBuffer sb) {
        sb.append(QUOTE).append(escapeQuotes(value)).append(QUOTE).append(VALUE_SEPARATOR);
    }

    private static String escapeQuotes(Object value) {
        return String.valueOf(value).replaceAll("\"", "\\\"");
    }
}
