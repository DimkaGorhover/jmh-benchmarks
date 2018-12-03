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
        return FormatDigitsUtil2.formatDouble_math_cached(
                ThreadLocalRandom.current().nextDouble(),
                2);
    }

    @Benchmark
    public double math_pow() {
        return FormatDigitsUtil1.formatDouble_math(
                ThreadLocalRandom.current().nextDouble(),
                2);
    }

    @Benchmark
    public double bidDecimal() {
        return FormatDigitsUtil3.formatDouble_BigDecimal(
                ThreadLocalRandom.current().nextDouble(),
                2);
    }
}
