package here.benchmarks;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

/**
 * @since 2019-09-06
 */
@SuppressWarnings("DefaultAnnotationParam")
@BenchmarkMode(Mode.AverageTime)
@State(Scope.Benchmark)
@Fork(4)
@Warmup(iterations = 4, time = 4, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 4, timeUnit = TimeUnit.SECONDS)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class NewInstanceBenchmark {

    private Factory factory;

    @Setup
    public void setup() {
        factory = new Factory();
    }

    @Benchmark
    public Object reflection() throws Exception {
        return Object.class.newInstance();
    }

    @Benchmark
    public Object classic() { return factory.create(); }

    static class Factory {

        Object create() { return new Object(); }
    }
}
