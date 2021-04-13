package benchmarks.increment;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;

import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
class AtomicLongFieldUpdaterIncrement implements Increment {

    private static final AtomicLongFieldUpdater<AtomicLongFieldUpdaterIncrement> FU = AtomicLongFieldUpdater
            .newUpdater(AtomicLongFieldUpdaterIncrement.class, "i");

    private volatile long i = 0;

    @Override
    public long i() {
        long value;
        do { value = FU.get(this); } while (!FU.weakCompareAndSet(this, value, value + 1));
        return value + 1;
    }
}
