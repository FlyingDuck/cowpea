package com.vvwyy.cowpea;

import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.Uninterruptibles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.concurrent.TimeUnit.MICROSECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

public abstract class TimeVortex {
    private static final Logger LOGGER = LoggerFactory.getLogger(TimeVortex.class);

    public static TimeVortex create(double taskPreSecond) {
        return create(taskPreSecond, SleepingStopwatch.createFromSystemTimer());
    }

    public static TimeVortex create(double taskPreSecond, SleepingStopwatch stopwatch) {
        TimeVortex timeVortex = new SmoothTimeVortex.SmoothConstant(stopwatch, 5*60);
        timeVortex.setRate(taskPreSecond);
        return timeVortex;
    }

    public static TimeVortex create(double taskPreSecond, long stablePeriod, TimeUnit timeUnit) {
        checkArgument(stablePeriod >= 0, "stablePeriod must not be negative: %s", stablePeriod);
        return create(taskPreSecond, stablePeriod, 3.0, timeUnit, SleepingStopwatch.createFromSystemTimer());
    }

    public static TimeVortex create(double taskPreSecond, long stablePeriod, double increaseFactor, TimeUnit timeUnit, SleepingStopwatch stopwatch) {
        TimeVortex timeVortex = new SmoothTimeVortex.SmoothLinear(stopwatch, stablePeriod, timeUnit,increaseFactor);
        timeVortex.setRate(taskPreSecond);
        return timeVortex;
    }


    private final SleepingStopwatch stopwatch;

    TimeVortex(SleepingStopwatch stopwatch) {
        this.stopwatch = Preconditions.checkNotNull(stopwatch);
    }

    public final void setRate(double taskPreSecond) {
        checkArgument(taskPreSecond > 0.0 && !Double.isNaN(taskPreSecond), "rate must be positive");
        synchronized (mutex()) {
            doSetRate(taskPreSecond);
        }
    }

    abstract void doSetRate(double taskPreSecond);

    public double owe() {
        return owe(1);
    }

    public double owe(int debts) {
        long microsToWait = adjust(debts);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Will wait for {} micros", microsToWait);
        }
        stopwatch.sleepMicrosUninterruptibly(microsToWait);
        return 1.0 * microsToWait / SECONDS.toMicros(1L);
    }

    final long adjust(int debts) {
        checkPermits(debts);
        synchronized (mutex()) {
            return adjustAndGetWaitLength(debts);
        }
    }

    public double repay() {
        return repay(1);
    }

    public double repay(int debts) {
        checkPermits(debts);
        synchronized (mutex()) {
            return adjustAndGetWaitLength(-debts);
        }
    }

    abstract long adjustAndGetWaitLength(int debts);


    // Can't be initialized in the constructor because mocks don't call the constructor.
    private volatile Object mutexDoNotUseDirectly;

    private Object mutex() {
        Object mutex = mutexDoNotUseDirectly;
        if (mutex == null) {
            synchronized (this) {
                mutex = mutexDoNotUseDirectly;
                if (mutex == null) {
                    mutexDoNotUseDirectly = mutex = new Object();
                }
            }
        }
        return mutex;
    }

    abstract static class SleepingStopwatch {
        protected SleepingStopwatch() {}

        /*
         * We always hold the mutex when calling this. TODO(cpovirk): Is that important? Perhaps we need
         * to guarantee that each call to reserveEarliestAvailable, etc. sees a value >= the previous?
         * Also, is it OK that we don't hold the mutex when sleeping?
         */
        protected abstract long readMicros(); // todo refactor to nowMicros

        protected abstract void sleepMicrosUninterruptibly(long micros);

        public static SleepingStopwatch createFromSystemTimer() {
            return new SleepingStopwatch() {
                final Stopwatch stopwatch = Stopwatch.createStarted();

                @Override
                protected long readMicros() {
                    return stopwatch.elapsed(MICROSECONDS);
                }

                @Override
                protected void sleepMicrosUninterruptibly(long micros) {
                    if (micros > 0) {
                        Uninterruptibles.sleepUninterruptibly(micros, MICROSECONDS);
                    }
                }
            };
        }
    }

    private static void checkPermits(int debts) {
        Preconditions.checkArgument(debts > 0, "Owe debts (%s) must be positive", debts);
    }



}
