package benchmarks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test for {@link SplitAndGetBenchmark}.
 */
@Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
class SplitAndGetBenchmarkTest {

    SplitAndGetBenchmark benchmark;

    /**
     * Method that will be executed before each {@link Test test} method.
     *
     * @see BeforeEach
     */
    @BeforeEach
    void setUp() {
        benchmark = new SplitAndGetBenchmark();
        benchmark.size = 1000;
        benchmark.pos = 10;
        benchmark.setup();
    }

    @Test
    void testSplit() {
        assertEquals("random_string_9", benchmark.split());
    }

    @Test
    void testStringTokenizer() {
        assertEquals("random_string_9", benchmark.stringTokenizer());
    }
}
