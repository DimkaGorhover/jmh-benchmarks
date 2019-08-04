package here.benchmarks.increment;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Control;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.LongAdder;

/*
--------------------------------------------------------------------------------------------------------------------
Benchmark                                                | (iterations) | Mode | Cnt |   Score |    Error |  Units |
--------------------------------------------------------------------------------------------------------------------
test_Simple                                              |        10000 | avgt |  16 |   4.230 | ±  0.088 |  us/op |
test_Simple:·gc.alloc.rate                               |        10000 | avgt |  16 |   0.002 | ±  0.001 | MB/sec |
test_Simple:·gc.alloc.rate.norm                          |        10000 | avgt |  16 |   0.001 | ±  0.001 |   B/op |
test_Simple:·gc.count                                    |        10000 | avgt |  16 |     ≈ 0 |          | counts |
test_Simple:·stack                                       |        10000 | avgt |     |     NaN |          |    --- |
--------------------------------------------------------------------------------------------------------------------
test_AtomicLongFieldUpdaterIncrement                     |        10000 | avgt |  16 | 157.380 | ±  4.794 |  us/op |
test_AtomicLongFieldUpdaterIncrement:·gc.alloc.rate      |        10000 | avgt |  16 |   0.002 | ±  0.001 | MB/sec |
test_AtomicLongFieldUpdaterIncrement:·gc.alloc.rate.norm |        10000 | avgt |  16 |   0.040 | ±  0.002 |   B/op |
test_AtomicLongFieldUpdaterIncrement:·gc.count           |        10000 | avgt |  16 |     ≈ 0 |          | counts |
test_AtomicLongFieldUpdaterIncrement:·stack              |        10000 | avgt |     |     NaN |          |    --- |
--------------------------------------------------------------------------------------------------------------------
test_VolatileIncrement                                   |        10000 | avgt |  16 | 158.852 | ±  5.663 |  us/op |
test_VolatileIncrement:·gc.alloc.rate                    |        10000 | avgt |  16 |   0.002 | ±  0.001 | MB/sec |
test_VolatileIncrement:·gc.alloc.rate.norm               |        10000 | avgt |  16 |   0.040 | ±  0.002 |   B/op |
test_VolatileIncrement:·gc.count                         |        10000 | avgt |  16 |     ≈ 0 |          | counts |
test_VolatileIncrement:·stack                            |        10000 | avgt |     |     NaN |          |    --- |
--------------------------------------------------------------------------------------------------------------------
test_AtomicLong                                          |        10000 | avgt |  16 | 159.442 | ±  5.616 |  us/op |
test_AtomicLong:·gc.alloc.rate                           |        10000 | avgt |  16 |   0.002 | ±  0.001 | MB/sec |
test_AtomicLong:·gc.alloc.rate.norm                      |        10000 | avgt |  16 |   0.040 | ±  0.002 |   B/op |
test_AtomicLong:·gc.count                                |        10000 | avgt |  16 |     ≈ 0 |          | counts |
test_AtomicLong:·stack                                   |        10000 | avgt |     |     NaN |          |    --- |
--------------------------------------------------------------------------------------------------------------------
test_LongAdder                                           |        10000 | avgt |  16 | 165.660 | ±  5.723 |  us/op |
test_LongAdder:·gc.alloc.rate                            |        10000 | avgt |  16 |   0.002 | ±  0.001 | MB/sec |
test_LongAdder:·gc.alloc.rate.norm                       |        10000 | avgt |  16 |   0.042 | ±  0.001 |   B/op |
test_LongAdder:·gc.count                                 |        10000 | avgt |  16 |     ≈ 0 |          | counts |
test_LongAdder:·stack                                    |        10000 | avgt |     |     NaN |          |    --- |
--------------------------------------------------------------------------------------------------------------------
test_SynchronizedBlock                                   |        10000 | avgt |  16 | 314.414 | ±  3.375 |  us/op |
test_SynchronizedBlock:·gc.alloc.rate                    |        10000 | avgt |  16 |   0.002 | ±  0.001 | MB/sec |
test_SynchronizedBlock:·gc.alloc.rate.norm               |        10000 | avgt |  16 |   0.080 | ±  0.002 |   B/op |
test_SynchronizedBlock:·gc.count                         |        10000 | avgt |  16 |     ≈ 0 |          | counts |
test_SynchronizedBlock:·stack                            |        10000 | avgt |     |     NaN |          |    --- |
--------------------------------------------------------------------------------------------------------------------
test_SynchronizedMethod                                  |        10000 | avgt |  16 | 319.316 | ±  8.467 |  us/op |
test_SynchronizedMethod:·gc.alloc.rate                   |        10000 | avgt |  16 |   0.002 | ±  0.001 | MB/sec |
test_SynchronizedMethod:·gc.alloc.rate.norm              |        10000 | avgt |  16 |   0.082 | ±  0.003 |   B/op |
test_SynchronizedMethod:·gc.count                        |        10000 | avgt |  16 |     ≈ 0 |          | counts |
test_SynchronizedMethod:·stack                           |        10000 | avgt |     |     NaN |          |    --- |
--------------------------------------------------------------------------------------------------------------------
*/

/**
 * @author Gorkhover D.
 * @since 2017-05-31
 */
@SuppressWarnings({"unused", "WeakerAccess", "DefaultAnnotationParam"})
@State(Scope.Group)
@BenchmarkMode(Mode.AverageTime)
@Fork(4)
@Warmup(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class IncrementBenchmark {

    @Param({"10000"})
    public int iterations;

    private Increment
            simpleIncrement,
            volatileIncrement,
            atomicLongFieldUpdaterIncrement,
            atomicLongIncrement,
            longAdderIncrement,
            synchronizedMethodIncrement,
            synchronizedBlockIncrement;

    @Setup
    public void setup() {
        simpleIncrement = new SimpleIncrement();
        volatileIncrement = new VolatileIncrement();
        atomicLongFieldUpdaterIncrement = new AtomicLongFieldUpdaterIncrement();
        atomicLongIncrement = new AtomicLongIncrement();
        longAdderIncrement = new LongAdderIncrement();
        synchronizedMethodIncrement = new SynchronizedMethodIncrement();
        synchronizedBlockIncrement = new SynchronizedBlockIncrement();
    }

    @Group("test_Simple")
    private void spin_Simple(Control control) {
        do { simpleIncrement.i(); } while (!control.stopMeasurement);
    }

    @Group("test_Simple")
    @Benchmark
    public long test_Simple() {
        long result = 0;
        for (int i = 0; i < iterations; i++)
            result += simpleIncrement.i();
        return result;
    }

    @Group("test_VolatileIncrement")
    public void spin_test_VolatileIncrement(Control control) {
        do { volatileIncrement.i(); } while (!control.stopMeasurement);
    }

    @Group("test_VolatileIncrement")
    @Benchmark
    public long test_VolatileIncrement() {
        long result = 0;
        for (int i = 0; i < iterations; i++)
            result += volatileIncrement.i();
        return result;
    }

    @Group("test_AtomicLongFieldUpdaterIncrement")
    public void spin_test_AtomicLongFieldUpdaterIncrement(Control control) {
        do { atomicLongFieldUpdaterIncrement.i(); } while (!control.stopMeasurement);
    }

    @Group("test_AtomicLongFieldUpdaterIncrement")
    @Benchmark
    public long test_AtomicLongFieldUpdaterIncrement() {
        long result = 0;
        for (int i = 0; i < iterations; i++)
            result += atomicLongFieldUpdaterIncrement.i();
        return result;
    }

    @Group("test_AtomicLong")
    private void spin_AtomicLong(Control control) {
        do { atomicLongIncrement.i(); } while (!control.stopMeasurement);
    }

    @Group("test_AtomicLong")
    @Benchmark
    public long test_AtomicLong() {
        long result = 0;
        for (int i = 0; i < iterations; i++)
            result += atomicLongIncrement.i();
        return result;
    }

    @Group("test_LongAdder")
    private void spin_LongAdder(Control control) {
        do { longAdderIncrement.i(); } while (!control.stopMeasurement);
    }

    @Group("test_LongAdder")
    @Benchmark
    public long test_LongAdder() {
        long result = 0;
        for (int i = 0; i < iterations; i++)
            result += longAdderIncrement.i();
        return result;
    }

    @Group("test_SynchronizedMethod")
    private void spin_SynchronizedMethod(Control control) {
        do { synchronizedMethodIncrement.i(); } while (!control.stopMeasurement);
    }

    @Group("test_SynchronizedMethod")
    @Benchmark
    public long test_SynchronizedMethod() {
        long result = 0;
        for (int i = 0; i < iterations; i++)
            result += synchronizedMethodIncrement.i();
        return result;
    }

    @Group("test_SynchronizedBlock")
    private void spin_SynchronizedBlock(Control control) {
        do { synchronizedBlockIncrement.i(); } while (!control.stopMeasurement);
    }

    @Group("test_SynchronizedBlock")
    @Benchmark
    public long test_SynchronizedBlock() {
        long result = 0;
        for (int i = 0; i < iterations; i++)
            result += synchronizedBlockIncrement.i();
        return result;
    }
}
