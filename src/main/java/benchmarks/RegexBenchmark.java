package benchmarks;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.CompilerControl;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.openjdk.jmh.annotations.CompilerControl.Mode.DONT_INLINE;

@SuppressWarnings({"DefaultAnnotationParam", "unused"})
@State(Scope.Thread)
@Measurement(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Warmup(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@BenchmarkMode(Mode.AverageTime)
@Fork(4)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class RegexBenchmark {

    private static final Pattern REGEX = Pattern.compile(
            "(channels:generations:)([a-zA-Z-]+):([a-zA-Z0-9.-_:]*)([A-Z]{2})");

    private static final ThreadLocal<Matcher> TLM = ThreadLocal.withInitial(() -> REGEX.matcher(""));

    @CompilerControl(DONT_INLINE)
    @Benchmark
    public boolean pattern() {
        return REGEX.matcher("channels:generations:PB").matches();
    }

    @CompilerControl(DONT_INLINE)
    @Benchmark
    public boolean tlm() {
        return TLM.get().reset("channels:generations:PB").matches();
    }
}
