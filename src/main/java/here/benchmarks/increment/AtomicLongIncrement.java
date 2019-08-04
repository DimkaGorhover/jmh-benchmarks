package here.benchmarks.increment;

import java.util.concurrent.atomic.AtomicLong;

class AtomicLongIncrement implements Increment {

    private final AtomicLong i = new AtomicLong();

    @Override
    public long i() { return i.incrementAndGet(); }
}
