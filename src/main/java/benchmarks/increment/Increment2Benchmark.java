package benchmarks.increment;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Control;

import java.util.concurrent.TimeUnit;

import static java.util.Objects.requireNonNull;

/*
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
public class Increment2Benchmark {

    @Param({"10000"})
    public int iterations;

    @Param({
            "simple",
            "volatile",
            "atomic_field_updater",
            "atomic",
            "long_adder",
            "synchronized_method",
            "synchronized_block"
    })
    public String type;

    private Increment increment;

    private static Increment forName(String name) {
        requireNonNull(name, "\"name\" cannot be null");
        switch (name) {
            case "simple": return new SimpleIncrement();
            case "volatile": return new VolatileIncrement();
            case "atomic_field_updater": return new AtomicLongFieldUpdaterIncrement();
            case "atomic": return new AtomicLongIncrement();
            case "long_adder": return new LongAdderIncrement();
            case "synchronized_method": return new SynchronizedMethodIncrement();
            case "synchronized_block": return new SynchronizedBlockIncrement();
        }
        throw new UnsupportedOperationException(new String(new char[]{175, 92, 95, 40, 12484, 41, 95, 47, 175}));
    }

    @Setup
    public void setup() {
        increment = forName(type);
    }

    @Group("test")
    private void spin(Control control) {
        do {
            increment.i();
        } while (!control.stopMeasurement);
    }

    @Group("test")
    @Benchmark
    public long test() {
        long result = 0;
        for (int i = 0; i < iterations; i++)
            result += increment.i();
        return result;
    }
}
