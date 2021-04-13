package benchmarks.increment;

import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
class SynchronizedBlockIncrement implements Increment {

    private long i = 0;

    @Override
    public long i() { synchronized (this) { return ++i; } }
}
