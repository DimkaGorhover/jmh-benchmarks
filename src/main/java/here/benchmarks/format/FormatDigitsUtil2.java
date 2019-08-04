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

package here.benchmarks.format;

/**
 * @author Horkhover Dmytro
 * @since 2018-11-30
 */
class FormatDigitsUtil2 {

    private static final double[] CACHE = new double[12];

    static {
        CACHE[0] = 1;
        for (int i = 1; i < CACHE.length; i++) {
            CACHE[i] = CACHE[i - 1] * 10;
        }
    }

    private FormatDigitsUtil2() { throw new UnsupportedOperationException(); }

    static double formatDouble_math_cached(double valueToFormat, int afterFloatingPoint) {
        final double v = CACHE[afterFloatingPoint];
        return Math.round(valueToFormat * v) / v;
    }

}
