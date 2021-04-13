package benchmarks.gaus;

class IfGausCalculator implements GausCalculator {
    @Override
    public long calc(long count) {

        if (count % 2 == 0)
            return (count + 1) * (count / 2);

        return (count) * ((count / 2) + 1);
    }
}
