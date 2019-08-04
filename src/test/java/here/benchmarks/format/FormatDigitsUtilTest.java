package here.benchmarks.format;

import here.benchmarks.format.FormatDigitsUtil;
import here.benchmarks.format.FormatDigitsUtil1;
import here.benchmarks.format.FormatDigitsUtil2;
import here.benchmarks.format.FormatDigitsUtil3;
import org.junit.jupiter.api.*;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import static here.benchmarks.format.FormatDigitsUtil.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for {@link FormatDigitsUtil};
 *
 * @author Horkhover Dmytro
 * @since 2018-11-29
 */
class FormatDigitsUtilTest {

    private static String format(int v) {
        char[] chars = new char[v += 2];
        chars[0] = '#';
        chars[1] = '.';
        for (int i = 2; i < v; i++) {
            chars[i] = '#';
        }
        return new String(chars);
    }

    @Test
    @DisplayName("FormatDouble")
    void test_FormatDouble() {

        IntStream.range(0, 12).forEach(afterF -> {

            DoubleStream.concat(
                    DoubleStream.of(0.574734),
                    ThreadLocalRandom.current().doubles(1 << 10)
            ).forEach(source -> {

                double expected = formatDouble(source, format(afterF));

                assertAll(
                        () -> assertEquals(expected, FormatDigitsUtil1.formatDouble_math(source, afterF)),
                        () -> assertEquals(expected, FormatDigitsUtil2.formatDouble_math_cached(source, afterF)),
                        () -> assertEquals(expected, FormatDigitsUtil3.formatDouble_BigDecimal(source, afterF))
                );
            });

        });
    }
}