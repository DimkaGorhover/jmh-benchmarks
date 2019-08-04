package here.benchmarks.increment;

class SynchronizedBlockIncrement implements Increment {

    private long i = 0;

    @Override
    public long i() { synchronized (this) { return ++i; } }
}
