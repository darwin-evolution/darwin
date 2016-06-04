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
package com.github.darwinevolution.darwin.jackson;

import com.github.darwinevolution.darwin.execution.result.evolutionary.EvolvedValueExecutionResult;
import com.github.darwinevolution.darwin.execution.result.protoplast.ProtoplastValueExecutionResult;
import com.github.darwinevolution.darwin.api.ResultConsumerConfiguration;
import com.github.darwinevolution.darwin.execution.ImplementationPreference;
import com.github.darwinevolution.darwin.execution.result.ResultType;
import com.github.darwinevolution.darwin.execution.result.comparison.ComparisonResult;
import org.assertj.core.util.Arrays;
import org.junit.Test;

public class JacksonEvolutionResultConsumerTest {

    @Test
    public void shouldPrintVerboseResult() {
        JacksonEvolutionResultConsumer<Integer> integerJacksonEvolutionResultConsumer = new JacksonEvolutionResultConsumer<Integer>();

        ComparisonResult<Integer> integerComparisonResult = new ComparisonResult<Integer>("testEvolution",
                ImplementationPreference.EVOLVED,
                new ProtoplastValueExecutionResult<Integer>(Arrays.array(1,2), 100, 6),
                new EvolvedValueExecutionResult<Integer>(Arrays.array(1,2), 130, 6),
                ResultType.OK);

        integerJacksonEvolutionResultConsumer.consumeResults(integerComparisonResult, ResultConsumerConfiguration.VERBOSE);
    }

    @Test
    public void shouldPrintSilentResult() {
        JacksonEvolutionResultConsumer<Integer> integerJacksonEvolutionResultConsumer = new JacksonEvolutionResultConsumer<Integer>();

        ComparisonResult<Integer> integerComparisonResult = new ComparisonResult<Integer>("testEvolution",
                ImplementationPreference.EVOLVED,
                new ProtoplastValueExecutionResult<Integer>(Arrays.array(1,2), 100, 6),
                new EvolvedValueExecutionResult<Integer>(Arrays.array(1,2), 130, 6),
                ResultType.OK);

        integerJacksonEvolutionResultConsumer.consumeResults(integerComparisonResult, ResultConsumerConfiguration.SILENT);
    }

}
