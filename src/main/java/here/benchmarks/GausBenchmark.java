package here.benchmarks;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

/*
----------------------------------------------------------------------------------
Benchmark                             | Mode | Cnt |  Score |    Error |   Units |
----------------------------------------------------------------------------------
gausIf                                | avgt |  16 |  6.335 | ±  0.596 |   ns/op |
gausIf:·gc.alloc.rate                 | avgt |  16 |  0.003 | ±  0.001 |  MB/sec |
gausIf:·gc.alloc.rate.norm            | avgt |  16 | ≈ 10⁻⁵ |          |    B/op |
gausIf:·gc.churn.G1_Eden_Space        | avgt |  16 |  1.958 | ±  5.447 |  MB/sec |
gausIf:·gc.churn.G1_Eden_Space.norm   | avgt |  16 |  0.003 | ±  0.007 |    B/op |
gausIf:·gc.count                      | avgt |  16 |  2.000 |          |  counts |
gausIf:·gc.time                       | avgt |  16 |  5.000 |          |      ms |
gausIf:·stack                         | avgt |     |    NaN |          |     --- |
gausIf:·threads.alive                 | avgt |  16 | 13.000 | ±  0.001 | threads |
gausIf:·threads.daemon                | avgt |  16 | 12.000 | ±  0.001 | threads |
gausIf:·threads.started               | avgt |  16 | 21.000 |          | threads |
----------------------------------------------------------------------------------
gausMath                              | avgt |  16 |  6.203 | ±  0.386 |   ns/op |
gausMath:·gc.alloc.rate               | avgt |  16 |  0.003 | ±  0.001 |  MB/sec |
gausMath:·gc.alloc.rate.norm          | avgt |  16 | ≈ 10⁻⁶ |          |    B/op |
gausMath:·gc.churn.G1_Eden_Space      | avgt |  16 |  3.948 | ±  7.192 |  MB/sec |
gausMath:·gc.churn.G1_Eden_Space.norm | avgt |  16 |  0.005 | ±  0.009 |    B/op |
gausMath:·gc.count                    | avgt |  16 |  4.000 |          |  counts |
gausMath:·gc.time                     | avgt |  16 |  8.000 |          |      ms |
gausMath:·stack                       | avgt |     |    NaN |          |     --- |
gausMath:·threads.alive               | avgt |  16 | 13.000 | ±  0.001 | threads |
gausMath:·threads.daemon              | avgt |  16 | 12.000 | ±  0.001 | threads |
gausMath:·threads.started             | avgt |  16 | 21.000 |          | threads |
----------------------------------------------------------------------------------
 */

/**
 * @since 2019-09-19
 */
@SuppressWarnings("DefaultAnnotationParam")
@Measurement(iterations = 4, time = 1, timeUnit = TimeUnit.SECONDS)
@Warmup(iterations = 4, time = 1, timeUnit = TimeUnit.SECONDS)
@BenchmarkMode(Mode.AverageTime)
@Fork(4)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@OperationsPerInvocation(4)
public class GausBenchmark {

    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    private static long gausMath(long count) {
        final long mod = count % 2;
        return (count + (1 ^ mod)) * ((count >> 2) + (1 & mod));
    }

    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    private static long gausIf(long count) {
        if (count % 2 == 0)
            return (count + 1) * (count / 2);
        return (count) * ((count / 2) + 1);
    }

    @Benchmark
    public long gausMath() {
        return gausMath(4_294_967_295L)
                + gausMath(294_967_295L)
                + gausMath(967_295L)
                + gausMath(295L);
    }

    @Benchmark
    public long gausIf() {
        return gausIf(4_294_967_295L)
                + gausIf(294_967_295L)
                + gausIf(967_295L)
                + gausIf(295L);
    }
}
