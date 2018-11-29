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

                .include("FormatDigitsUtilBenchmark")

                .addProfiler(GCProfiler.class)
                .addProfiler(HotspotThreadProfiler.class)
                .addProfiler(StackProfiler.class)

                .verbosity(VerboseMode.EXTRA)
                .build();

        new Runner(opt).run();
    }
}
