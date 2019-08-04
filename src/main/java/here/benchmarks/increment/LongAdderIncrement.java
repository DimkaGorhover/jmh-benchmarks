package here.benchmarks.increment;

import java.util.concurrent.atomic.LongAdder;

class LongAdderIncrement implements Increment {

    private final LongAdder i = new LongAdder();

    @Override
    public long i() {
        i.increment();
        return i.longValue();
    }
}
