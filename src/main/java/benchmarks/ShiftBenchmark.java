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

package benchmarks;

import org.openjdk.jmh.annotations.*;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;


/*
----------------------------------------------------------------
Benchmark            | Mode | Cnt |   Score |    Error | Units |
----------------------------------------------------------------
test_primitive_shift | avgt |  16 |  21.212 | ±  8.229 | ns/op |
test_shift           | avgt |  16 |  44.654 | ±  7.312 | ns/op |
test_pow             | avgt |  16 | 115.304 | ± 23.546 | ns/op |
----------------------------------------------------------------



------------------------------------------------------------------------------------------------------
Benchmark                                             | Mode | Cnt |    Score |      Error |   Units |
------------------------------------------------------------------------------------------------------
test_primitive_shift                                  | avgt |  16 |   21.212 | ±    8.229 |   ns/op |
test_primitive_shift:·gc.alloc.rate                   | avgt |  16 | 3282.528 | ± 1194.248 |  MB/sec |
test_primitive_shift:·gc.alloc.rate.norm              | avgt |  16 |   24.000 | ±    0.001 |    B/op |
test_primitive_shift:·gc.churn.PS_Eden_Space          | avgt |  16 | 3354.749 | ± 1265.384 |  MB/sec |
test_primitive_shift:·gc.churn.PS_Eden_Space.norm     | avgt |  16 |   24.483 | ±    2.204 |    B/op |
test_primitive_shift:·gc.churn.PS_Survivor_Space      | avgt |  16 |    0.101 | ±    0.077 |  MB/sec |
test_primitive_shift:·gc.churn.PS_Survivor_Space.norm | avgt |  16 |    0.001 | ±    0.001 |    B/op |
test_primitive_shift:·gc.count                        | avgt |  16 |  120.000 |            |  counts |
test_primitive_shift:·gc.time                         | avgt |  16 |  146.000 |            |      ms |
test_primitive_shift:·stack                           | avgt |     |      NaN |            |     --- |
test_primitive_shift:·threads.alive                   | avgt |  16 |    8.000 | ±    0.001 | threads |
test_primitive_shift:·threads.daemon                  | avgt |  16 |    7.000 | ±    0.001 | threads |
test_primitive_shift:·threads.started                 | avgt |  16 |   16.000 |            | threads |
------------------------------------------------------------------------------------------------------
test_shift                                            | avgt |  16 |   44.654 | ±    7.312 |   ns/op |
test_shift:·gc.alloc.rate                             | avgt |  16 | 1387.080 | ±  194.810 |  MB/sec |
test_shift:·gc.alloc.rate.norm                        | avgt |  16 |   24.000 | ±    0.001 |    B/op |
test_shift:·gc.churn.PS_Eden_Space                    | avgt |  16 | 1412.039 | ±  331.636 |  MB/sec |
test_shift:·gc.churn.PS_Eden_Space.norm               | avgt |  16 |   24.331 | ±    4.251 |    B/op |
test_shift:·gc.churn.PS_Survivor_Space                | avgt |  16 |    0.038 | ±    0.044 |  MB/sec |
test_shift:·gc.churn.PS_Survivor_Space.norm           | avgt |  16 |    0.001 | ±    0.001 |    B/op |
test_shift:·gc.count                                  | avgt |  16 |   48.000 |            |  counts |
test_shift:·gc.time                                   | avgt |  16 |   34.000 |            |      ms |
test_shift:·stack                                     | avgt |     |      NaN |            |     --- |
test_shift:·threads.alive                             | avgt |  16 |    8.000 | ±    0.001 | threads |
test_shift:·threads.daemon                            | avgt |  16 |    7.000 | ±    0.001 | threads |
test_shift:·threads.started                           | avgt |  16 |   16.000 |            | threads |
------------------------------------------------------------------------------------------------------
test_pow                                              | avgt |  16 |  115.304 | ±   23.546 |   ns/op |
test_pow:·gc.alloc.rate                               | avgt |  16 | 2003.992 | ±  370.594 |  MB/sec |
test_pow:·gc.alloc.rate.norm                          | avgt |  16 |   88.000 | ±    0.001 |    B/op |
test_pow:·gc.churn.PS_Eden_Space                      | avgt |  16 | 2088.107 | ±  414.458 |  MB/sec |
test_pow:·gc.churn.PS_Eden_Space.norm                 | avgt |  16 |   91.799 | ±    8.287 |    B/op |
test_pow:·gc.churn.PS_Survivor_Space                  | avgt |  16 |    0.037 | ±    0.029 |  MB/sec |
test_pow:·gc.churn.PS_Survivor_Space.norm             | avgt |  16 |    0.002 | ±    0.001 |    B/op |
test_pow:·gc.count                                    | avgt |  16 |   59.000 |            |  counts |
test_pow:·gc.time                                     | avgt |  16 |   68.000 |            |      ms |
test_pow:·stack                                       | avgt |     |      NaN |            |     --- |
test_pow:·threads.alive                               | avgt |  16 |    8.000 | ±    0.001 | threads |
test_pow:·threads.daemon                              | avgt |  16 |    7.000 | ±    0.001 | threads |
test_pow:·threads.started                             | avgt |  16 |   16.000 |            | threads |
------------------------------------------------------------------------------------------------------
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

    private static final BigInteger
            ONE = BigInteger.valueOf(1),
            TWO = BigInteger.valueOf(2);

    @Benchmark
    public long test_primitive_shift() { return BigInteger.valueOf(1 << 10).longValueExact(); }

    @Benchmark
    public long test_shift() { return ONE.shiftLeft(10).longValueExact(); }

    @Benchmark
    public long test_pow() { return TWO.pow(10).longValueExact(); }
}
