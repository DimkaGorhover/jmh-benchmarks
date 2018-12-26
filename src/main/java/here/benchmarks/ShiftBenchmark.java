/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package here.benchmarks;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;


/*
------------------------------------------------------------------
Benchmark                 | Mode | Cnt | Score |   Error | Units |
------------------------------------------------------------------
ShiftBenchmark.test_shift | avgt |  16 | 5.075 | ± 0.488 | ns/op |
ShiftBenchmark.test_pow   | avgt |  16 | 5.067 | ± 0.705 | ns/op |
ShiftBenchmark.test_multi | avgt |  16 | 5.547 | ± 0.779 | ns/op |
------------------------------------------------------------------


@CompilerControl(CompilerControl.Mode.DONT_INLINE)
------------------------------------------------------------------
Benchmark                 | Mode | Cnt | Score |   Error | Units |
------------------------------------------------------------------
ShiftBenchmark.test_pow   | avgt |  16 | 7.485 | ± 1.046 | ns/op |
ShiftBenchmark.test_shift | avgt |  16 | 8.232 | ± 1.092 | ns/op |
ShiftBenchmark.test_multi | avgt |  16 | 8.448 | ± 1.333 | ns/op |
------------------------------------------------------------------
 */

/**
 * @author Horkhover Dmytro
 * @since 2018-12-26
 */
@SuppressWarnings({"DefaultAnnotationParam", "WeakerAccess"})
@Measurement(iterations = 4, time = 1, timeUnit = TimeUnit.SECONDS)
@Warmup(iterations = 4, time = 1, timeUnit = TimeUnit.SECONDS)
@BenchmarkMode(Mode.AverageTime)
@Fork(4)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class ShiftBenchmark {

    @Benchmark
    public int test_shift() { return shift(); }

    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    private int shift() { return 3 << 5; }

    @Benchmark
    public int test_multi() { return multi(); }

    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    private int multi() { return 3 * 32; }

    @Benchmark
    public int test_pow() { return pow(); }

    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    private int pow() { return (int) (3 * Math.pow(2, 5)); }
}
