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
import io.darwin.execution.ImplementationPreferenceType;
import io.darwin.execution.result.ExecutionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jEvolutionResultConsumer<T> extends EvolutionResultConsumer<T> {

    private static final Logger log = LoggerFactory.getLogger("darwin.evolution");

    @Override
    public void consumeResults(String evolutionName, ImplementationPreferenceType implementationPreferenceType, ExecutionResult<T> currentExecutionResult, ExecutionResult<T> evolvedExecutionResult) {
        StringBuffer sb = new StringBuffer();

        sb.append(evolutionName).append("|");
        sb.append(implementationPreferenceType.name()).append("|");
        sb.append(currentExecutionResult.getResult()).append("|");
        sb.append(currentExecutionResult.getExecutionTime()).append("|");
        sb.append(currentExecutionResult.getException()).append("|");
        sb.append(evolvedExecutionResult.getResult()).append("|");
        sb.append(evolvedExecutionResult.getExecutionTime()).append("|");
        sb.append(evolvedExecutionResult.getException()).append("|");
        sb.append(compareResults(implementationPreferenceType, currentExecutionResult, evolvedExecutionResult).getResultType().name());

        log.info(sb.toString());
    }
}
