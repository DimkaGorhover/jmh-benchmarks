package here.benchmarks;

import here.benchmarks.increment.IncrementBenchmark;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class IncrementBenchmarkTest {

    @Test
    void name() {
        IncrementBenchmark b = new IncrementBenchmark();
        b.iterations = 100;
        b.setup();

        int expected = 5050;
        assertAll(
                () -> assertEquals(expected, b.test_Simple()),
                () -> assertEquals(expected, b.test_VolatileIncrement()),
                () -> assertEquals(expected, b.test_AtomicLongFieldUpdaterIncrement()),
                () -> assertEquals(expected, b.test_AtomicLong()),
                () -> assertEquals(expected, b.test_LongAdder()),
                () -> assertEquals(expected, b.test_SynchronizedMethod()),
                () -> assertEquals(expected, b.test_SynchronizedBlock())
        );
    }
}