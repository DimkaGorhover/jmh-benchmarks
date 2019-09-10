package here.benchmarks;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/*
--------------------------------------------------------------------------------
Benchmark                    | (pow) | Mode | Cnt |   Score |    Error | Units |
PowBenchmark.binaryRecursion |    10 | avgt |  16 | 178.445 | ±  8.424 | ns/op |
PowBenchmark.loop            |    10 | avgt |  16 | 206.389 | ± 75.351 | ns/op |
PowBenchmark.math            |    10 | avgt |  16 | 210.494 | ± 17.344 | ns/op |
--------------------------------------------------------------------------------
 */

/**
 * @since 2019-09-10
 */
@SuppressWarnings({"DefaultAnnotationParam", "WeakerAccess"})
@BenchmarkMode(Mode.AverageTime)
@State(Scope.Benchmark)
@Fork(4)
@Warmup(iterations = 4, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 1, timeUnit = TimeUnit.SECONDS)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class PowBenchmark {

    //@Param({"8", "64", "128", "1024", "4096"})
    @Param("10")
    public int pow;

    public double value;

    @Setup(Level.Invocation)
    public void setup() { value = ThreadLocalRandom.current().nextDouble(); }

    public static double binaryRecursionPow(double v, int pow) {
        switch (pow) {
            case -4: return v / v / v / v / v / v;
            case -3: return v / v / v / v / v;
            case -2: return v / v / v / v;
            case -1: return v / v / v;
            case 0: return 1;
            case 1: return v;
            case 2: return v * v;
            case 3: return v * v * v;
            case 4: return v * v * v * v;
        }
        double half = binaryRecursionPow(v, pow / 2);
        return half * half * binaryRecursionPow(v, pow % 2);
    }

    @Benchmark
    public double math() { return Math.pow(value, pow); }

    @Benchmark
    public double binaryRecursion() { return binaryRecursionPow(value, pow); }

    @Benchmark
    public double loop() {
        double half = value;
        for (int i = 1; i < pow / 2; i++)
            half *= value;
        return half * half * (pow % 2 == 0 ? 1 : value);
    }
}
