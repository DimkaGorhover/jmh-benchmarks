package here.benchmarks;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

final class FormatDigitsUtil {

    private static final double[] CACHE = new double[12];

    static {
        CACHE[0] = 1;
        for (int i = 1; i < CACHE.length; i++) {
            CACHE[i] = CACHE[i - 1] * 10;
        }
    }

    private FormatDigitsUtil() { throw new UnsupportedOperationException(); }

    static double formatDouble(double valueToFormat, String pattern) {
        return Double.parseDouble(new DecimalFormat(pattern).format(valueToFormat));
    }

    static double formatDouble_math_cached(double valueToFormat, int afterFloatingPoint) {
        final double v = CACHE[afterFloatingPoint];
        return Math.round(valueToFormat * v) / v;
    }

    static double formatDouble_math(double valueToFormat, int afterFloatingPoint) {
        final double v = Math.pow(10, afterFloatingPoint);
        return Math.round(valueToFormat * v) / v;
    }

    static double formatDouble_BigDecimal(double valueToFormat, int afterFloatingPoint) {
        return BigDecimal.valueOf(valueToFormat)
                .setScale(afterFloatingPoint, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
