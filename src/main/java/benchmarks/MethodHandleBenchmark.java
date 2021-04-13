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

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.requireNonNull;

/*
--------------------------------------------------------------------------------------------
Benchmark                       | (size) | Mode | Cnt |      Score |       Error |   Units |
--------------------------------------------------------------------------------------------
MethodHandle_static_invoke      |   1000 | avgt |  16 |   1219.146 | ±    21.228 |   ns/op |
SimpleAccess                    |   1000 | avgt |  16 |   1228.924 | ±    24.107 |   ns/op |
MethodHandle_static_invokeExact |   1000 | avgt |  16 |   1229.609 | ±    29.682 |   ns/op |
MethodHandle_invoke             |   1000 | avgt |  16 |   8408.901 | ±   163.890 |   ns/op |
MethodHandle_invokeExact        |   1000 | avgt |  16 |   8575.691 | ±   233.164 |   ns/op |
Reflection_static               |   1000 | avgt |  16 |  23852.799 | ±   570.826 |   ns/op |
Reflection                      |   1000 | avgt |  16 | 543871.723 | ± 13535.875 |   ns/op |
--------------------------------------------------------------------------------------------

----------------------------------------------------------------------------------------------------------------
Benchmark                                           | (size) | Mode | Cnt |      Score |       Error |   Units |
----------------------------------------------------------------------------------------------------------------
SimpleAccess                                        |   1000 | avgt |  16 |   1228.924 | ±    24.107 |   ns/op |
SimpleAccess:·gc.alloc.rate                         |   1000 | avgt |  16 |      0.002 | ±     0.001 |  MB/sec |
SimpleAccess:·gc.alloc.rate.norm                    |   1000 | avgt |  16 |     ≈ 10⁻⁴ |             |    B/op |
SimpleAccess:·gc.count                              |   1000 | avgt |  16 |        ≈ 0 |             |  counts |
SimpleAccess:·stack                                 |   1000 | avgt |     |        NaN |             |     --- |
SimpleAccess:·threads.alive                         |   1000 | avgt |  16 |     12.000 | ±     0.001 | threads |
SimpleAccess:·threads.daemon                        |   1000 | avgt |  16 |     11.000 | ±     0.001 | threads |
SimpleAccess:·threads.started                       |   1000 | avgt |  16 |     20.000 |             | threads |
----------------------------------------------------------------------------------------------------------------
MethodHandle_static_invoke                          |   1000 | avgt |  16 |   1219.146 | ±    21.228 |   ns/op |
MethodHandle_static_invoke:·gc.alloc.rate           |   1000 | avgt |  16 |      0.002 | ±     0.001 |  MB/sec |
MethodHandle_static_invoke:·gc.alloc.rate.norm      |   1000 | avgt |  16 |     ≈ 10⁻⁴ |             |    B/op |
MethodHandle_static_invoke:·gc.count                |   1000 | avgt |  16 |        ≈ 0 |             |  counts |
MethodHandle_static_invoke:·stack                   |   1000 | avgt |     |        NaN |             |     --- |
MethodHandle_static_invoke:·threads.alive           |   1000 | avgt |  16 |     12.000 | ±     0.001 | threads |
MethodHandle_static_invoke:·threads.daemon          |   1000 | avgt |  16 |     11.000 | ±     0.001 | threads |
MethodHandle_static_invoke:·threads.started         |   1000 | avgt |  16 |     20.000 |             | threads |
----------------------------------------------------------------------------------------------------------------
MethodHandle_static_invokeExact                     |   1000 | avgt |  16 |   1229.609 | ±    29.682 |   ns/op |
MethodHandle_static_invokeExact:·gc.alloc.rate      |   1000 | avgt |  16 |      0.002 | ±     0.001 |  MB/sec |
MethodHandle_static_invokeExact:·gc.alloc.rate.norm |   1000 | avgt |  16 |     ≈ 10⁻³ |             |    B/op |
MethodHandle_static_invokeExact:·gc.count           |   1000 | avgt |  16 |        ≈ 0 |             |  counts |
MethodHandle_static_invokeExact:·stack              |   1000 | avgt |     |        NaN |             |     --- |
MethodHandle_static_invokeExact:·threads.alive      |   1000 | avgt |  16 |     12.000 | ±     0.001 | threads |
MethodHandle_static_invokeExact:·threads.daemon     |   1000 | avgt |  16 |     11.000 | ±     0.001 | threads |
MethodHandle_static_invokeExact:·threads.started    |   1000 | avgt |  16 |     20.000 |             | threads |
----------------------------------------------------------------------------------------------------------------
MethodHandle_invoke                                 |   1000 | avgt |  16 |   8408.901 | ±   163.890 |   ns/op |
MethodHandle_invoke:·gc.alloc.rate                  |   1000 | avgt |  16 |      0.002 | ±     0.001 |  MB/sec |
MethodHandle_invoke:·gc.alloc.rate.norm             |   1000 | avgt |  16 |      0.002 | ±     0.001 |    B/op |
MethodHandle_invoke:·gc.count                       |   1000 | avgt |  16 |        ≈ 0 |             |  counts |
MethodHandle_invoke:·stack                          |   1000 | avgt |     |        NaN |             |     --- |
MethodHandle_invoke:·threads.alive                  |   1000 | avgt |  16 |     12.000 | ±     0.001 | threads |
MethodHandle_invoke:·threads.daemon                 |   1000 | avgt |  16 |     11.000 | ±     0.001 | threads |
MethodHandle_invoke:·threads.started                |   1000 | avgt |  16 |     20.000 |             | threads |
----------------------------------------------------------------------------------------------------------------
MethodHandle_invokeExact                            |   1000 | avgt |  16 |   8575.691 | ±   233.164 |   ns/op |
MethodHandle_invokeExact:·gc.alloc.rate             |   1000 | avgt |  16 |      0.002 | ±     0.001 |  MB/sec |
MethodHandle_invokeExact:·gc.alloc.rate.norm        |   1000 | avgt |  16 |      0.002 | ±     0.001 |    B/op |
MethodHandle_invokeExact:·gc.count                  |   1000 | avgt |  16 |        ≈ 0 |             |  counts |
MethodHandle_invokeExact:·stack                     |   1000 | avgt |     |        NaN |             |     --- |
MethodHandle_invokeExact:·threads.alive             |   1000 | avgt |  16 |     12.000 | ±     0.001 | threads |
MethodHandle_invokeExact:·threads.daemon            |   1000 | avgt |  16 |     11.000 | ±     0.001 | threads |
MethodHandle_invokeExact:·threads.started           |   1000 | avgt |  16 |     20.000 |             | threads |
----------------------------------------------------------------------------------------------------------------
Reflection                                          |   1000 | avgt |  16 | 543871.723 | ± 13535.875 |   ns/op |
Reflection:·gc.alloc.rate                           |   1000 | avgt |  16 |   1702.576 | ±    43.028 |  MB/sec |
Reflection:·gc.alloc.rate.norm                      |   1000 | avgt |  16 | 151904.853 | ±    99.782 |    B/op |
Reflection:·gc.churn.PS_Eden_Space                  |   1000 | avgt |  16 |   1712.283 | ±    95.112 |  MB/sec |
Reflection:·gc.churn.PS_Eden_Space.norm             |   1000 | avgt |  16 | 152714.730 | ±  6082.897 |    B/op |
Reflection:·gc.churn.PS_Survivor_Space              |   1000 | avgt |  16 |      0.071 | ±     0.026 |  MB/sec |
Reflection:·gc.churn.PS_Survivor_Space.norm         |   1000 | avgt |  16 |      6.281 | ±     2.291 |    B/op |
Reflection:·gc.count                                |   1000 | avgt |  16 |    203.000 |             |  counts |
Reflection:·gc.time                                 |   1000 | avgt |  16 |    307.000 |             |      ms |
Reflection:·stack                                   |   1000 | avgt |     |        NaN |             |     --- |
Reflection:·threads.alive                           |   1000 | avgt |  16 |     12.000 | ±     0.001 | threads |
Reflection:·threads.daemon                          |   1000 | avgt |  16 |     11.000 | ±     0.001 | threads |
Reflection:·threads.started                         |   1000 | avgt |  16 |     20.000 |             | threads |
----------------------------------------------------------------------------------------------------------------
Reflection_static                                   |   1000 | avgt |  16 |  23852.799 | ±   570.826 |   ns/op |
Reflection_static:·gc.alloc.rate                    |   1000 | avgt |  16 |   8160.981 | ±   209.684 |  MB/sec |
Reflection_static:·gc.alloc.rate.norm               |   1000 | avgt |  16 |  31338.539 | ±   645.743 |    B/op |
Reflection_static:·gc.churn.PS_Eden_Space           |   1000 | avgt |  16 |   8212.279 | ±   217.292 |  MB/sec |
Reflection_static:·gc.churn.PS_Eden_Space.norm      |   1000 | avgt |  16 |  31534.450 | ±   623.606 |    B/op |
Reflection_static:·gc.churn.PS_Survivor_Space       |   1000 | avgt |  16 |      0.349 | ±     0.059 |  MB/sec |
Reflection_static:·gc.churn.PS_Survivor_Space.norm  |   1000 | avgt |  16 |      1.341 | ±     0.230 |    B/op |
Reflection_static:·gc.count                         |   1000 | avgt |  16 |    975.000 |             |  counts |
Reflection_static:·gc.time                          |   1000 | avgt |  16 |   1520.000 |             |      ms |
Reflection_static:·stack                            |   1000 | avgt |     |        NaN |             |     --- |
Reflection_static:·threads.alive                    |   1000 | avgt |  16 |     12.000 | ±     0.001 | threads |
Reflection_static:·threads.daemon                   |   1000 | avgt |  16 |     11.000 | ±     0.001 | threads |
Reflection_static:·threads.started                  |   1000 | avgt |  16 |     20.000 |             | threads |
----------------------------------------------------------------------------------------------------------------
 */

/**
 * @author Horkhover Dmytro
 * @since 2018-11-30
 */
@SuppressWarnings({"unused", "WeakerAccess", "DefaultAnnotationParam"})
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@Fork(4)
@Warmup(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class MethodHandleBenchmark {

    private static final MethodHandle STATIC_MH;
    private static final Method       REFLECTION_METHOD;

    static {
        try {
            STATIC_MH = MethodHandles.lookup().findGetter(User.class, "id", int.class);
            REFLECTION_METHOD = User.class.getDeclaredMethod("getId");
        } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Param({"1000"})
    public int size;

    public MethodHandle mh;
    public User[]       users;

    @Setup
    public void setup() {
        users = new User[size];
        for (int i = 0; i < size; i++)
            users[i] = new User(i, "user_name_" + i);
        try {
            mh = MethodHandles.lookup().findGetter(User.class, "id", int.class);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Benchmark
    public int meter_SimpleAccess() {
        int result = 0;
        for (User user : users)
            result += user.getId();
        return result;
    }

    @Benchmark
    public int meter_Reflection() throws Throwable {
        int result = 0;
        for (User user : users)
            result += (int) User.class.getDeclaredMethod("getId").invoke(user);
        return result;
    }

    @Benchmark
    public int meter_Reflection_static() throws Throwable {
        int result = 0;
        for (User user : users)
            result += (int) REFLECTION_METHOD.invoke(user);
        return result;
    }

    @Benchmark
    public int meter_MethodHandle_invoke() throws Throwable {
        int result = 0;
        for (User user : users)
            result += (int) mh.invoke(user);
        return result;
    }

    @Benchmark
    public int meter_MethodHandle_invokeExact() throws Throwable {
        int result = 0;
        for (User user : users)
            result += (int) mh.invokeExact(user);
        return result;
    }

    @Benchmark
    public int meter_MethodHandle_static_invoke() throws Throwable {
        int result = 0;
        for (User user : users)
            result += (int) STATIC_MH.invoke(user);
        return result;
    }

    @Benchmark
    public int meter_MethodHandle_static_invokeExact() throws Throwable {
        int result = 0;
        for (User user : users)
            result += (int) STATIC_MH.invokeExact(user);
        return result;
    }

    public static class User {
        final int    id;
        final String name;

        public User(int id, String name) {
            this.id = id;
            this.name = requireNonNull(name, "name");
        }

        public int getId() { return id; }

        public String getName() { return name; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof User)) return false;
            User user = (User) o;
            return id == user.id &&
                    Objects.equals(name, user.name);
        }

        @Override
        public int hashCode() {
            int result = id;
            result = 31 * result + name.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return "SimpleUser{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
