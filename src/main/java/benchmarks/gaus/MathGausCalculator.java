package benchmarks.gaus;

class MathGausCalculator implements GausCalculator {

    @Override
    public long calc(long count) {
        final long mod = count % 2;
        return (count + (1 ^ mod)) * ((count >> 2) + (1 & mod));
    }
}
