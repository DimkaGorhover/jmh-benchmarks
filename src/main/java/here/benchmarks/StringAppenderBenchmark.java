package here.benchmarks;

import org.openjdk.jmh.annotations.*;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/*

https://shipilev.net/jvm/anatomy-quarks/19-lock-elision/

-XX:+EliminateLocks
-XX:+DoEscapeAnalysis



----------------------------------------------------------------------
Benchmark     | (size) | Mode | Cnt |    Score |     Error |   Units |
----------------------------------------------------------------------
StringBuffer  |    128 | avgt |  16 |  893.510 | ±  62.450 |   ns/op |
StringBuilder |    128 | avgt |  16 |  902.773 | ±  49.063 |   ns/op |
----------------------------------------------------------------------



---------------------------------------------------------------------------------------------------
Benchmark                                  | (size) | Mode | Cnt |    Score |     Error |   Units |
---------------------------------------------------------------------------------------------------
StringBuffer                               |    128 | avgt |  16 |  893.510 | ±  62.450 |   ns/op |
StringBuffer:·gc.alloc.rate                |    128 | avgt |  16 | 9867.163 | ± 618.594 |  MB/sec |
StringBuffer:·gc.alloc.rate.norm           |    128 | avgt |  16 | 1296.000 | ±  14.571 |    B/op |
StringBuffer:·gc.churn.G1_Eden_Space       |    128 | avgt |  16 | 9920.308 | ± 609.263 |  MB/sec |
StringBuffer:·gc.churn.G1_Eden_Space.norm  |    128 | avgt |  16 | 1303.096 | ±  15.580 |    B/op |
StringBuffer:·gc.churn.G1_Old_Gen          |    128 | avgt |  16 |    0.025 | ±   0.003 |  MB/sec |
StringBuffer:·gc.churn.G1_Old_Gen.norm     |    128 | avgt |  16 |    0.003 | ±   0.001 |    B/op |
StringBuffer:·gc.count                     |    128 | avgt |  16 | 1612.000 |           |  counts |
StringBuffer:·gc.time                      |    128 | avgt |  16 | 1285.000 |           |      ms |
StringBuffer:·stack                        |    128 | avgt |     |      NaN |           |     --- |
StringBuffer:·threads.alive                |    128 | avgt |  16 |   13.000 | ±   0.001 | threads |
StringBuffer:·threads.daemon               |    128 | avgt |  16 |   12.000 | ±   0.001 | threads |
StringBuffer:·threads.started              |    128 | avgt |  16 |   21.000 |           | threads |
---------------------------------------------------------------------------------------------------
StringBuilder                              |    128 | avgt |  16 |  902.773 | ±  49.063 |   ns/op |
StringBuilder:·gc.alloc.rate               |    128 | avgt |  16 | 9694.920 | ± 499.801 |  MB/sec |
StringBuilder:·gc.alloc.rate.norm          |    128 | avgt |  16 | 1288.000 | ±   0.001 |    B/op |
StringBuilder:·gc.churn.G1_Eden_Space      |    128 | avgt |  16 | 9731.404 | ± 509.575 |  MB/sec |
StringBuilder:·gc.churn.G1_Eden_Space.norm |    128 | avgt |  16 | 1292.808 | ±   6.511 |    B/op |
StringBuilder:·gc.churn.G1_Old_Gen         |    128 | avgt |  16 |    0.022 | ±   0.004 |  MB/sec |
StringBuilder:·gc.churn.G1_Old_Gen.norm    |    128 | avgt |  16 |    0.003 | ±   0.001 |    B/op |
StringBuilder:·gc.count                    |    128 | avgt |  16 | 1416.000 |           |  counts |
StringBuilder:·gc.time                     |    128 | avgt |  16 | 1260.000 |           |      ms |
StringBuilder:·stack                       |    128 | avgt |     |      NaN |           |     --- |
StringBuilder:·threads.alive               |    128 | avgt |  16 |   13.000 | ±   0.001 | threads |
StringBuilder:·threads.daemon              |    128 | avgt |  16 |   12.000 | ±   0.001 | threads |
StringBuilder:·threads.started             |    128 | avgt |  16 |   21.000 |           | threads |
---------------------------------------------------------------------------------------------------
 */

@SuppressWarnings({"DefaultAnnotationParam", "unused"})
@Measurement(iterations = 4, time = 4, timeUnit = TimeUnit.SECONDS)
@Warmup(iterations = 4, time = 4, timeUnit = TimeUnit.SECONDS)
@BenchmarkMode(Mode.AverageTime)
@Fork(4)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
public class StringAppenderBenchmark {

    @Param({"128"})
    public int size;

    private String[] list;

    @Setup
    public void setup() {
        list = new String[size / 16];
        for (int i = 0; i < size / 16; i++) {
            list[i] = UUID.randomUUID().toString();
        }
    }

    @SuppressWarnings("ForLoopReplaceableByForEach")
    @Benchmark
    public String StringBuilder() {
        StringBuilder sb = new StringBuilder(size);
        for (int i = 0; i < list.length; i++) {
            sb.append(list[i]);
        }
        return sb.toString();
    }

    @SuppressWarnings({"ForLoopReplaceableByForEach", "StringBufferMayBeStringBuilder"})
    @Benchmark
    public String StringBuffer() {
        StringBuffer sb = new StringBuffer(size);
        for (int i = 0; i < list.length; i++) {
            sb.append(list[i]);
        }
        return sb.toString();
    }
}
