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

import org.openjdk.jmh.profile.GCProfiler;
import org.openjdk.jmh.profile.HotspotThreadProfiler;
import org.openjdk.jmh.profile.StackProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.*;

public class App {

    public static void main(String[] args) throws RunnerException, CommandLineOptionException {

        final CommandLineOptions cli = new CommandLineOptions(args);

        Options opt = new OptionsBuilder()

                .threads(cli.getThreads().orElse(Runtime.getRuntime().availableProcessors()))

                .include("ShiftBenchmark")

                .addProfiler(GCProfiler.class)
                .addProfiler(HotspotThreadProfiler.class)
                .addProfiler(StackProfiler.class)

                .verbosity(VerboseMode.EXTRA)
                .build();

        new Runner(opt).run();
    }
}
