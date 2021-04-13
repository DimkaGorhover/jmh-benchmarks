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

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for {@link ShiftBenchmark};
 *
 * @author Horkhover Dmytro
 * @since 2018-12-26
 */
class ShiftBenchmarkTest {

    @Test
    @DisplayName("Test_shift")
    void test_Test_shift() {
        ShiftBenchmark b = new ShiftBenchmark();
        assertEquals(1024, b.test_pow());
        assertEquals(1024, b.test_primitive_shift());
        assertEquals(1024, b.test_shift());
    }
}