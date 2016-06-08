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
package com.github.darwinevolution.darwin.typesafeconfig;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.github.darwinevolution.darwin.execution.ImplementationPreference;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
public class TypesafeConfigImplementationChooserBenchmark {

    @Benchmark
    public String chooseEnabledImplementation() {
        Config config = ConfigFactory.load("benchmark1K.conf");
        ImplementationPreference evolvedEnabled = new TypesafeConfigImplementationChooser(config).chooseImplementation("EvolvedEnabled");
        return evolvedEnabled.name();
    }

    @Benchmark
    public String chooseDisabledImplementation() {
        Config config = ConfigFactory.load("benchmark1K.conf");
        ImplementationPreference evolvedEnabled = new TypesafeConfigImplementationChooser(config).chooseImplementation("EvolvedDisabled");
        return evolvedEnabled.name();
    }

    @Benchmark
    public String chooseMissingImplementation() {
        Config config = ConfigFactory.load("benchmark1K.conf");
        ImplementationPreference evolvedEnabled = new TypesafeConfigImplementationChooser(config).chooseImplementation("EvolvedMissing");
        return evolvedEnabled.name();
    }

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
                .include(TypesafeConfigImplementationChooserBenchmark.class.getSimpleName())
                .warmupIterations(10)
                .forks(1)
                .threads(10)
                .timeUnit(TimeUnit.MILLISECONDS)
                .shouldFailOnError(true).shouldDoGC(true).build();
        new Runner(options).run();

        System.exit(1);
    }
}
