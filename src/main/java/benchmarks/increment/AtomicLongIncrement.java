package benchmarks.increment;

import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
class AtomicLongIncrement implements Increment {

    private final AtomicLong i = new AtomicLong();

    @Override
    public long i() { return i.incrementAndGet(); }
}
