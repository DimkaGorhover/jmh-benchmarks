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

package here.benchmarks.lombok;

import lombok.RequiredArgsConstructor;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

/*
--------------------------------------------------------------------------------------------------------------------------
Benchmark                                                                   | Mode | Cnt |    Score |     Error |  Units |
--------------------------------------------------------------------------------------------------------------------------
LombokConstructorBenchmark.test_LombokUser                                  | avgt |  16 |   18.348 | ±   0.711 |  ns/op |
LombokConstructorBenchmark.test_LombokUser:·gc.alloc.rate                   | avgt |  16 | 8880.498 | ± 337.780 | MB/sec |
LombokConstructorBenchmark.test_LombokUser:·gc.alloc.rate.norm              | avgt |  16 |   24.000 | ±   0.001 |   B/op |
LombokConstructorBenchmark.test_LombokUser:·gc.churn.PS_Eden_Space          | avgt |  16 | 8938.132 | ± 329.940 | MB/sec |
LombokConstructorBenchmark.test_LombokUser:·gc.churn.PS_Eden_Space.norm     | avgt |  16 |   24.157 | ±   0.119 |   B/op |
LombokConstructorBenchmark.test_LombokUser:·gc.churn.PS_Survivor_Space      | avgt |  16 |    0.319 | ±   0.036 | MB/sec |
LombokConstructorBenchmark.test_LombokUser:·gc.churn.PS_Survivor_Space.norm | avgt |  16 |    0.001 | ±   0.001 |   B/op |
LombokConstructorBenchmark.test_LombokUser:·gc.count                        | avgt |  16 | 1901.000 |           | counts |
LombokConstructorBenchmark.test_LombokUser:·gc.time                         | avgt |  16 | 2451.000 |           |     ms |
LombokConstructorBenchmark.test_LombokUser:·stack                           | avgt |     |      NaN |           |    --- |
--------------------------------------------------------------------------------------------------------------------------
LombokConstructorBenchmark.test_User                                        | avgt |  16 |   18.001 | ±   0.358 |  ns/op |
LombokConstructorBenchmark.test_User:·gc.alloc.rate                         | avgt |  16 | 9041.272 | ± 172.283 | MB/sec |
LombokConstructorBenchmark.test_User:·gc.alloc.rate.norm                    | avgt |  16 |   24.000 | ±   0.001 |   B/op |
LombokConstructorBenchmark.test_User:·gc.churn.PS_Eden_Space                | avgt |  16 | 9096.480 | ± 180.314 | MB/sec |
LombokConstructorBenchmark.test_User:·gc.churn.PS_Eden_Space.norm           | avgt |  16 |   24.146 | ±   0.055 |   B/op |
LombokConstructorBenchmark.test_User:·gc.churn.PS_Survivor_Space            | avgt |  16 |    0.322 | ±   0.065 | MB/sec |
LombokConstructorBenchmark.test_User:·gc.churn.PS_Survivor_Space.norm       | avgt |  16 |    0.001 | ±   0.001 |   B/op |
LombokConstructorBenchmark.test_User:·gc.count                              | avgt |  16 | 1932.000 |           | counts |
LombokConstructorBenchmark.test_User:·gc.time                               | avgt |  16 | 2272.000 |           |     ms |
LombokConstructorBenchmark.test_User:·stack                                 | avgt |     |      NaN |           |    --- |
--------------------------------------------------------------------------------------------------------------------------
 */

/**
 * benchmark for {@link RequiredArgsConstructor}
 *
 * @author Gorkhover D.
 * @see RequiredArgsConstructor
 * @since 2017-11-13
 */
@SuppressWarnings({"unused", "Duplicates"})
@BenchmarkMode(Mode.AverageTime)
@Fork(4)
@Warmup(iterations = 4, time = 4, timeUnit = SECONDS)
@Measurement(iterations = 4, time = 4, timeUnit = SECONDS)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class LombokConstructorBenchmark {

    @Benchmark
    public User test_User() { return new User(1, "1"); }

    @Benchmark
    public LombokUser test_LombokUser() { return new LombokUser(1, "1"); }

}
