package benchmarks.json;

import benchmarks.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.NonNull;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Group;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/*
----------------------------------------------
Benchmark (avgt)   | Score |   Error | Units |
------- read ---------------------------------
ObjectReader       | 5.954 | ± 0.306 | us/op |
ObjectMapper_read  | 7.045 | ± 3.876 | us/op |
------ write ---------------------------------
ObjectWriter       | 4.171 | ± 0.167 | us/op |
ObjectMapper_write | 5.007 | ± 2.640 | us/op |
----------------------------------------------
 */

/*
---------------------------------------------------------------------------------------------------------------
Benchmark                                                       | Mode | Cnt |    Score |     Error |   Units |
---------------------------------------------------------------------------------------------------------------
Jackson2Benchmark.ObjectMapper                                  | avgt |  16 |    6.026 | ±   3.256 |   us/op |
Jackson2Benchmark.ObjectMapper:ObjectMapper_read                | avgt |  16 |    7.045 | ±   3.876 |   us/op |
Jackson2Benchmark.ObjectMapper:ObjectMapper_write               | avgt |  16 |    5.007 | ±   2.640 |   us/op |
Jackson2Benchmark.ObjectMapper:·gc.alloc.rate                   | avgt |  16 | 1735.687 | ± 329.432 |  MB/sec |
Jackson2Benchmark.ObjectMapper:·gc.alloc.rate.norm              | avgt |  16 | 1511.711 | ±  14.665 |    B/op |
Jackson2Benchmark.ObjectMapper:·gc.churn.G1_Eden_Space          | avgt |  16 | 1747.209 | ± 341.490 |  MB/sec |
Jackson2Benchmark.ObjectMapper:·gc.churn.G1_Eden_Space.norm     | avgt |  16 | 1517.044 | ±  40.889 |    B/op |
Jackson2Benchmark.ObjectMapper:·gc.churn.G1_Survivor_Space      | avgt |  16 |    0.041 | ±   0.013 |  MB/sec |
Jackson2Benchmark.ObjectMapper:·gc.churn.G1_Survivor_Space.norm | avgt |  16 |    0.036 | ±   0.009 |    B/op |
Jackson2Benchmark.ObjectMapper:·gc.count                        | avgt |  16 |  452.000 |           |  counts |
Jackson2Benchmark.ObjectMapper:·gc.time                         | avgt |  16 |  279.000 |           |      ms |
Jackson2Benchmark.ObjectMapper:·stack                           | avgt |     |      NaN |           |     --- |
Jackson2Benchmark.ObjectMapper:·threads.alive                   | avgt |  16 |   14.000 | ±   0.001 | threads |
Jackson2Benchmark.ObjectMapper:·threads.daemon                  | avgt |  16 |   13.000 | ±   0.001 | threads |
Jackson2Benchmark.ObjectMapper:·threads.started                 | avgt |  16 |   22.000 |           | threads |
---------------------------------------------------------------------------------------------------------------
Jackson2Benchmark.ObjectReader                                  | avgt |  16 |    5.954 | ±   0.306 |   us/op |
Jackson2Benchmark.ObjectReader:·gc.alloc.rate                   | avgt |  16 | 2454.806 | ± 137.376 |  MB/sec |
Jackson2Benchmark.ObjectReader:·gc.alloc.rate.norm              | avgt |  16 | 2384.098 | ±  25.906 |    B/op |
Jackson2Benchmark.ObjectReader:·gc.churn.G1_Eden_Space          | avgt |  16 | 2474.959 | ± 147.064 |  MB/sec |
Jackson2Benchmark.ObjectReader:·gc.churn.G1_Eden_Space.norm     | avgt |  16 | 2403.523 | ±  48.216 |    B/op |
Jackson2Benchmark.ObjectReader:·gc.churn.G1_Survivor_Space      | avgt |  16 |    0.050 | ±   0.020 |  MB/sec |
Jackson2Benchmark.ObjectReader:·gc.churn.G1_Survivor_Space.norm | avgt |  16 |    0.049 | ±   0.021 |    B/op |
Jackson2Benchmark.ObjectReader:·gc.count                        | avgt |  16 |  494.000 |           |  counts |
Jackson2Benchmark.ObjectReader:·gc.time                         | avgt |  16 |  313.000 |           |      ms |
Jackson2Benchmark.ObjectReader:·stack                           | avgt |     |      NaN |           |     --- |
Jackson2Benchmark.ObjectReader:·threads.alive                   | avgt |  16 |   14.000 | ±   0.001 | threads |
Jackson2Benchmark.ObjectReader:·threads.daemon                  | avgt |  16 |   13.000 | ±   0.001 | threads |
Jackson2Benchmark.ObjectReader:·threads.started                 | avgt |  16 |   22.000 |           | threads |
---------------------------------------------------------------------------------------------------------------
Jackson2Benchmark.ObjectWriter                                  | avgt |  16 |    4.171 | ±   0.167 |   us/op |
Jackson2Benchmark.ObjectWriter:·gc.alloc.rate                   | avgt |  16 | 1251.862 | ±  48.745 |  MB/sec |
Jackson2Benchmark.ObjectWriter:·gc.alloc.rate.norm              | avgt |  16 |  856.119 | ±   0.003 |    B/op |
Jackson2Benchmark.ObjectWriter:·gc.churn.G1_Eden_Space          | avgt |  16 | 1261.807 | ±  51.881 |  MB/sec |
Jackson2Benchmark.ObjectWriter:·gc.churn.G1_Eden_Space.norm     | avgt |  16 |  863.133 | ±  23.035 |    B/op |
Jackson2Benchmark.ObjectWriter:·gc.churn.G1_Survivor_Space      | avgt |  16 |    0.027 | ±   0.008 |  MB/sec |
Jackson2Benchmark.ObjectWriter:·gc.churn.G1_Survivor_Space.norm | avgt |  16 |    0.019 | ±   0.005 |    B/op |
Jackson2Benchmark.ObjectWriter:·gc.count                        | avgt |  16 |  276.000 |           |  counts |
Jackson2Benchmark.ObjectWriter:·gc.time                         | avgt |  16 |  175.000 |           |      ms |
Jackson2Benchmark.ObjectWriter:·stack                           | avgt |     |      NaN |           |     --- |
Jackson2Benchmark.ObjectWriter:·threads.alive                   | avgt |  16 |   14.000 | ±   0.001 | threads |
Jackson2Benchmark.ObjectWriter:·threads.daemon                  | avgt |  16 |   13.000 | ±   0.001 | threads |
Jackson2Benchmark.ObjectWriter:·threads.started                 | avgt |  16 |   22.000 |           | threads |
---------------------------------------------------------------------------------------------------------------
 */
@SuppressWarnings({"unused", "WeakerAccess", "DefaultAnnotationParam"})
@State(Scope.Group)
@BenchmarkMode(Mode.AverageTime)
@Fork(4)
@Warmup(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class Jackson2Benchmark {

    public ObjectMapper objectMapper;
    public ObjectReader objectReader;
    public ObjectWriter objectWriter;

    public JacksonUser user;

    public String json;

    @NonNull
    private static JacksonUser createUser() {
        User.Gender[] genders = User.Gender.values();
        return JacksonUser.builder()
                .id(ThreadLocalRandom.current().nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE))
                .name(UUID.randomUUID().toString())
                .phones(new String[]{
                        UUID.randomUUID().toString(),
                        UUID.randomUUID().toString(),
                        UUID.randomUUID().toString()
                })
                .cards(Arrays.asList(
                        ThreadLocalRandom.current().nextDouble(),
                        ThreadLocalRandom.current().nextDouble(),
                        ThreadLocalRandom.current().nextDouble()
                ))
                .gender(genders[ThreadLocalRandom.current().nextInt(0, genders.length)])
                .build();
    }

    @Setup
    public void setup() {
        objectMapper = new ObjectMapper();
        objectReader = new ObjectMapper().readerFor(JacksonUser.class);
        objectWriter = new ObjectMapper().writerFor(JacksonUser.class);
        user = createUser();
        json = createUser().toString();
    }

    @Benchmark
    @Group("ObjectMapper")
    public User ObjectMapper_read() throws IOException {
        return objectMapper.readValue(json, JacksonUser.class);
    }

    @Benchmark
    @Group("ObjectReader")
    public User ObjectReader_read() throws IOException {
        return objectReader.readValue(json);
    }

    @Benchmark
    @Group("ObjectMapper")
    public byte[] ObjectMapper_write() throws IOException {
        return objectMapper.writeValueAsBytes(user);
    }

    @Benchmark
    @Group("ObjectWriter")
    public byte[] ObjectWriter_write() throws IOException {
        return objectWriter.writeValueAsBytes(user);
    }
}
