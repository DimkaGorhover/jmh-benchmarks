package here.benchmarks.increment;

class VolatileIncrement implements Increment {

    private volatile long i = 0;

    @Override
    public long i() { return ++i; }
}
