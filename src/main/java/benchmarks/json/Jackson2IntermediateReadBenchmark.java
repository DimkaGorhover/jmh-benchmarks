package benchmarks.json;

import benchmarks.User;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
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
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*
-------------------------------------------------------------------
Benchmark    | (size) | Mode | Cnt |    Score |     Error | Units |
-------------------------------------------------------------------
ObjectMapper |    100 | avgt |  16 | 1658.969 | ± 187.831 | us/op |
ObjectReader |    100 | avgt |  16 | 1213.834 | ±  57.018 | us/op | x1.37 faster
-------------------------------------------------------------------



------------------------------------------------------------------------------------------------------
Benchmark                                 | (size) | Mode | Cnt |      Score |       Error |   Units |
------------------------------------------------------------------------------------------------------
ObjectMapper:·gc.alloc.rate               |    100 | avgt |  16 |   1367.047 | ±   141.239 |  MB/sec |
ObjectReader:·gc.alloc.rate               |    100 | avgt |  16 |    914.992 | ±    41.818 |  MB/sec |
------------------------------------------------------------------------------------------------------
ObjectMapper:·gc.alloc.rate.norm          |    100 | avgt |  16 | 366710.402 | ±  2486.299 |    B/op |
ObjectReader:·gc.alloc.rate.norm          |    100 | avgt |  16 | 181089.324 | ±  1545.433 |    B/op |
------------------------------------------------------------------------------------------------------
ObjectReader:·gc.churn.G1_Eden_Space      |    100 | avgt |  16 |    915.928 | ±    81.016 |  MB/sec |
ObjectMapper:·gc.churn.G1_Eden_Space      |    100 | avgt |  16 |   1372.511 | ±   187.531 |  MB/sec |
------------------------------------------------------------------------------------------------------
ObjectMapper:·gc.churn.G1_Eden_Space.norm |    100 | avgt |  16 | 367525.406 | ± 24325.578 |    B/op |
ObjectReader:·gc.churn.G1_Eden_Space.norm |    100 | avgt |  16 | 181158.288 | ± 12073.637 |    B/op |
------------------------------------------------------------------------------------------------------



------------------------------------------------------------------------------------------------------
Benchmark                                 | (size) | Mode | Cnt |      Score |       Error |   Units |
------------------------------------------------------------------------------------------------------
ObjectMapper                              |    100 | avgt |  16 |   1658.969 | ±   187.831 |   us/op |
ObjectMapper:·gc.alloc.rate               |    100 | avgt |  16 |   1367.047 | ±   141.239 |  MB/sec |
ObjectMapper:·gc.alloc.rate.norm          |    100 | avgt |  16 | 366710.402 | ±  2486.299 |    B/op |
ObjectMapper:·gc.churn.G1_Eden_Space      |    100 | avgt |  16 |   1372.511 | ±   187.531 |  MB/sec |
ObjectMapper:·gc.churn.G1_Eden_Space.norm |    100 | avgt |  16 | 367525.406 | ± 24325.578 |    B/op |
ObjectMapper:·gc.churn.G1_Old_Gen         |    100 | avgt |  16 |      0.079 | ±     0.031 |  MB/sec |
ObjectMapper:·gc.churn.G1_Old_Gen.norm    |    100 | avgt |  16 |     21.605 | ±     8.902 |    B/op |
ObjectMapper:·gc.count                    |    100 | avgt |  16 |    101.000 |             |  counts |
ObjectMapper:·gc.time                     |    100 | avgt |  16 |    555.000 |             |      ms |
ObjectMapper:·stack                       |    100 | avgt |     |        NaN |             |     --- |
ObjectMapper:·threads.alive               |    100 | avgt |  16 |     13.000 | ±     0.001 | threads |
ObjectMapper:·threads.daemon              |    100 | avgt |  16 |     12.000 | ±     0.001 | threads |
ObjectMapper:·threads.started             |    100 | avgt |  16 |     21.000 |             | threads |
------------------------------------------------------------------------------------------------------
ObjectReader                              |    100 | avgt |  16 |   1213.834 | ±    57.018 |   us/op |
ObjectReader:·gc.alloc.rate               |    100 | avgt |  16 |    914.992 | ±    41.818 |  MB/sec |
ObjectReader:·gc.alloc.rate.norm          |    100 | avgt |  16 | 181089.324 | ±  1545.433 |    B/op |
ObjectReader:·gc.churn.G1_Eden_Space      |    100 | avgt |  16 |    915.928 | ±    81.016 |  MB/sec |
ObjectReader:·gc.churn.G1_Eden_Space.norm |    100 | avgt |  16 | 181158.288 | ± 12073.637 |    B/op |
ObjectReader:·gc.churn.G1_Old_Gen         |    100 | avgt |  16 |      0.052 | ±     0.020 |  MB/sec |
ObjectReader:·gc.churn.G1_Old_Gen.norm    |    100 | avgt |  16 |     10.154 | ±     3.870 |    B/op |
ObjectReader:·gc.count                    |    100 | avgt |  16 |     84.000 |             |  counts |
ObjectReader:·gc.time                     |    100 | avgt |  16 |    499.000 |             |      ms |
ObjectReader:·stack                       |    100 | avgt |     |        NaN |             |     --- |
ObjectReader:·threads.alive               |    100 | avgt |  16 |     13.000 | ±     0.001 | threads |
ObjectReader:·threads.daemon              |    100 | avgt |  16 |     12.000 | ±     0.001 | threads |
ObjectReader:·threads.started             |    100 | avgt |  16 |     21.000 |             | threads |
------------------------------------------------------------------------------------------------------
*/
@State(Scope.Group)
@BenchmarkMode(Mode.AverageTime)
@Fork(4)
@Warmup(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class Jackson2IntermediateReadBenchmark {

    @Param({"10000"})
    int size;

    ObjectMapper objectMapper;
    ObjectReader objectReader;
    JsonFactory jsonFactory;

    byte[] json;

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
    public void setup() throws IOException {

        ObjectWriter objectWriter = new ObjectMapper()
                .writerFor(JacksonUser[].class)
                .withDefaultPrettyPrinter();

        JacksonUser[] users = IntStream.range(0, size)
                .mapToObj(ignore -> createUser())
                .toArray(JacksonUser[]::new);

        json = objectWriter.writeValueAsBytes(users);

        objectMapper = new ObjectMapper();
        objectReader = new ObjectMapper().readerFor(JacksonUser.class);
        jsonFactory = objectWriter.getFactory();
    }

    private InputStream json() {
        return new ByteArrayInputStream(json);
    }

    private String readJsonString() throws IOException {
        try (BufferedReader bf = new BufferedReader(new InputStreamReader(json(), StandardCharsets.UTF_8.name()))) {
            return bf.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }

    @Benchmark
    @Group("ObjectMapper")
    public Map<Integer, User> ObjectMapper() throws IOException {
        Map<Integer, User> map = new HashMap<>(size);
        JacksonUser[] users = objectMapper.readValue(readJsonString(), JacksonUser[].class);
        for (User user : users) {
            map.put(user.getId(), user);
        }
        return map;
    }

    @Benchmark
    @Group("ObjectReader")
    public Map<Integer, User> ObjectReader() throws IOException {
        Map<Integer, User> map = new HashMap<>(size);
        try (JsonParser jsonParser = jsonFactory.createParser(json())) {
            if (jsonParser.nextToken() != JsonToken.START_ARRAY) {
                throw new IllegalStateException("Expected content to be an array");
            }
            while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                User user = objectReader.readValue(jsonParser);
                map.put(user.getId(), user);
            }
        }
        return map;
    }
}
