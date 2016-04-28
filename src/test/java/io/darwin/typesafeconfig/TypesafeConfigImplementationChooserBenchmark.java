package io.darwin.typesafeconfig;


import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.darwin.execution.ImplementationPreference;
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
