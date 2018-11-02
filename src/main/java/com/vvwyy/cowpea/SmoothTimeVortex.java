package com.vvwyy.cowpea;

public class SmoothTimeVortex extends TimeVortex {
    // todo


    SmoothTimeVortex(SleepingStopwatch stopwatch) {
        super(stopwatch);
    }

    long reserveEarliestAvailable(int permits, long nowMicros) {
        return 0;
    }
}
