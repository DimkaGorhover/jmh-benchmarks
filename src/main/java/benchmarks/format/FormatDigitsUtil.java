//@formatter:off
package benchmarks.format;

import java.text.DecimalFormat;

final class FormatDigitsUtil
{
    static double formatDouble(double valueToFormat, String pattern)
    {
        return Double.parseDouble(new DecimalFormat(pattern).format(valueToFormat));
    }
}
