package here.benchmarks;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for {@link PowBenchmark}
 *
 * @since 2019-09-10
 */
class PowBenchmarkTest {

    @Test
    @DisplayName("Setup")
    void test_Setup() throws Exception {
        for (int i = 0; i < 1 << 13; i++) {
            PowBenchmark b = new PowBenchmark();
            b.pow = 10;
            b.setup();
            assertEquals(b.math(), b.loop(), 0.000000000000001);
            assertEquals(b.math(), b.recursion(), 0.000000000000001);
        }
    }
}