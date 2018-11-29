package here.benchmarks;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author Horkhover Dmytro
 * @since 2018-11-29
 */
@SuppressWarnings({"unused", "DefaultAnnotationParam"})
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@Fork(4)
@Warmup(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class FormatDigitsUtilBenchmark {

    @Benchmark
    public double string() {
        return FormatDigitsUtil.formatDouble(
                ThreadLocalRandom.current().nextDouble(),
                "#.##");
    }

    @Benchmark
    public double math_cached() {
        return FormatDigitsUtil.formatDouble_math_cached(
                ThreadLocalRandom.current().nextDouble(),
                2);
    }

    @Benchmark
    public double math_pow() {
        return FormatDigitsUtil.formatDouble_math(
                ThreadLocalRandom.current().nextDouble(),
                2);
    }

    @Benchmark
    public double bidDecimal() {
        return FormatDigitsUtil.formatDouble_BigDecimal(
                ThreadLocalRandom.current().nextDouble(),
                2);
    }
}
