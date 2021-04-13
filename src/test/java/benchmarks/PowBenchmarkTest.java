package benchmarks;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for {@link PowBenchmark}
 *
 * @since 2019-09-10
 */
class PowBenchmarkTest {

    private static final double DELTA = Math.pow(10, -15);

    @Test
    @DisplayName("Setup")
    void test_Setup() throws Exception {

        for (int i = 0; i < 1 << 13; i++) {

            PowBenchmark b = new PowBenchmark();
            b.pow = 10;
            b.setup();

            assertEquals(b.math(), b.loop(), DELTA);
            assertEquals(b.math(), b.binaryRecursion(), DELTA);
        }
    }
}