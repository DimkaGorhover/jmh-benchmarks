package here.benchmarks.increment;

class SynchronizedMethodIncrement implements Increment {

    private long i = 0;

    public synchronized long i() { return ++i; }
}
