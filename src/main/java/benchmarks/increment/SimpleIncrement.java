package benchmarks.increment;

class SimpleIncrement implements Increment {

    private long i = 0;

    @Override
    public long i() { return ++i; }
}
