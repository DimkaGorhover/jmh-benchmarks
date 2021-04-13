package benchmarks.increment;

import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
class SynchronizedMethodIncrement implements Increment {

    private long i = 0;

    public synchronized long i() { return ++i; }
}
