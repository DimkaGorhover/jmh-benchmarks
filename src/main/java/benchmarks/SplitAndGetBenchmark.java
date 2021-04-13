package benchmarks;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.CompilerControl;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.openjdk.jmh.annotations.CompilerControl.Mode.DONT_INLINE;

/*
----------------------------------------------------------------------------------
Benchmark       | (pos) | (size) | Mode | Cnt |     Score |      Error |   Units |
----------------------------------------------------------------------------------
stringTokenizer |    15 |   1000 | avgt |  16 |  1528.099 | ±   45.460 |   ns/op |
split           |    15 |   1000 | avgt |  16 | 85167.892 | ± 5815.190 |   ns/op |
----------------------------------------------------------------------------------
split           |   985 |   1000 | avgt |  16 | 81915.135 | ±  839.943 |   ns/op |
stringTokenizer |   985 |   1000 | avgt |  16 | 87190.490 | ± 5234.684 |   ns/op |
----------------------------------------------------------------------------------



------------------------------------------------------------------------------------------------------
Benchmark                           | (pos) | (size) | Mode | Cnt |     Score |      Error |   Units |
------------------------------------------------------------------------------------------------------
stringTokenizer:·gc.alloc.rate      |    15 |   1000 | avgt |  16 |  4312.704 | ±  124.525 |  MB/sec |
stringTokenizer:·gc.alloc.rate.norm |    15 |   1000 | avgt |  16 |  1080.000 | ±    0.001 |    B/op |
split:·gc.alloc.rate                |    15 |   1000 | avgt |  16 |  7068.035 | ±  440.985 |  MB/sec |
split:·gc.alloc.rate.norm           |    15 |   1000 | avgt |  16 | 98312.022 | ±    0.001 |    B/op |
------------------------------------------------------------------------------------------------------
stringTokenizer:·gc.alloc.rate      |   985 |   1000 | avgt |  16 |  5466.733 | ±  317.956 |  MB/sec |
stringTokenizer:·gc.alloc.rate.norm |   985 |   1000 | avgt |  16 | 78000.022 | ±    0.001 |    B/op |
split:·gc.alloc.rate                |   985 |   1000 | avgt |  16 |  7324.264 | ±   73.450 |  MB/sec |
split:·gc.alloc.rate.norm           |   985 |   1000 | avgt |  16 | 98312.021 | ±    0.001 |    B/op |
------------------------------------------------------------------------------------------------------
 */

@SuppressWarnings({"DefaultAnnotationParam", "unused"})
@State(Scope.Thread)
@Measurement(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Warmup(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@BenchmarkMode(Mode.AverageTime)
@Fork(4)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class SplitAndGetBenchmark {

    private static final String DELIMITER = "/";

    @Param({"1000"})
    public int size;
    @Param({"15", "985"})
    public int pos;

    public String str;

    @Setup
    public void setup() {
        str = IntStream.range(0, size)
                .mapToObj(value -> "random_string_" + value)
                .collect(Collectors.joining(DELIMITER, DELIMITER, ""));
    }

    @CompilerControl(DONT_INLINE)
    @Benchmark
    public String stringTokenizer() {
        StringTokenizer st = new StringTokenizer(str, DELIMITER);
        int cursor = 0;
        String result = null;
        while (st.hasMoreTokens() && cursor < pos) {
            result = st.nextToken();
            cursor++;
        }
        return result;
    }

    @CompilerControl(DONT_INLINE)
    @Benchmark
    public String split() {
        return str.split(DELIMITER)[pos];
    }
}
