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
import io.darwin.execution.result.ResultType;
import io.darwin.execution.result.comparison.ComparisonResult;
import io.darwin.execution.result.evolutionary.EvolvedValueExecutionResult;
import io.darwin.execution.result.protoplast.ProtoplastValueExecutionResult;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.All)
public class Slf4jEvolutionResultConsumerBenchmark {

    @State(Scope.Thread)
    public static class Context {
        Slf4jEvolutionResultConsumer slf4jEvolutionResultConsumer;
        ComparisonResult<Integer> integerComparisonResult;

        @Setup
        public void initClient() {
            slf4jEvolutionResultConsumer = new Slf4jEvolutionResultConsumer();
            integerComparisonResult = new ComparisonResult<Integer>("testEvolution",
                    ImplementationPreference.EVOLVED,
                    new ProtoplastValueExecutionResult<Integer>(Arrays.asList(-1,-2).toArray(), 100, -6),
                    new EvolvedValueExecutionResult<Integer>(Arrays.asList(-1,-2).toArray(), 130, -6),
                    ResultType.OK);
        }
    }

    @Benchmark
    public String consumeVerboseOkResult(Context context) {
        StringBuffer stringBuffer = new StringBuffer();
        context.slf4jEvolutionResultConsumer.consumeResults(context.integerComparisonResult, stringBuffer, ResultConsumerConfiguration.VERBOSE);
        return stringBuffer.toString();
    }


    @Benchmark
    public String consumeSilentOkResult(Context context) {
        StringBuffer stringBuffer = new StringBuffer();
        context.slf4jEvolutionResultConsumer.consumeResults(context.integerComparisonResult, stringBuffer, ResultConsumerConfiguration.SILENT);
        return stringBuffer.toString();
    }

    public static void main(String[] args) throws Exception {

        Options options = new OptionsBuilder()
                .include(Slf4jEvolutionResultConsumerBenchmark.class.getSimpleName())
                .warmupIterations(10)
                .forks(1)
                .threads(10)
                .timeUnit(TimeUnit.NANOSECONDS)
                .shouldFailOnError(true).shouldDoGC(true).build();
        new Runner(options).run();

        System.exit(1);
    }
}
