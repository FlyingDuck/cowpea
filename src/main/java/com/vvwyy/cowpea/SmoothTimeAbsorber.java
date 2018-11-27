/*
 *   Copyright 2018 Bennett Dong
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.vvwyy.cowpea;

import java.util.concurrent.TimeUnit;

public abstract class SmoothTimeAbsorber extends TimeAbsorber {

    static final class SmoothConstant extends SmoothTimeAbsorber {

        final double maxWaitSeconds;

        SmoothConstant(SleepingStopwatch stopwatch, double maxWaitSeconds) {
            super(stopwatch);
            this.maxWaitSeconds = maxWaitSeconds;
        }

        @Override
        void doSetRate(double taskPreSecond, double stableIntervalMicros) {
            double oldMaxDebts = this.maxDebts;
            this.maxDebts = this.maxWaitSeconds * taskPreSecond;
            if (oldMaxDebts == Double.POSITIVE_INFINITY) {
                this.owedDebts = this.maxDebts;
            } else {
                this.owedDebts = (oldMaxDebts == 0.0) ? 0.0 : owedDebts * maxDebts / oldMaxDebts;
            }

        }

        long owedDebtsToWaitTime() {
            return (long) (this.owedDebts * this.stableIntervalMicros);
        }
    }

    static final class SmoothLinear extends SmoothTimeAbsorber {
        private long stablePeriodMicros;
        private double slope;
        private double thresholdDebts;
        private double increaseFactor;


        SmoothLinear(SleepingStopwatch stopwatch, long increasingPeriod, TimeUnit timeUnit, double increaseFactor) {
            super(stopwatch);
            this.stableIntervalMicros = timeUnit.toMicros(increasingPeriod);
            this.increaseFactor = increaseFactor;
        }

        void doSetRate(double taskPreSecond, double stableIntervalMicros) {
            double oldMaxDebts = this.maxDebts;
            double increaseIntervalMicros = this.stableIntervalMicros * increaseFactor;
            this.thresholdDebts = stablePeriodMicros / this.stableIntervalMicros;
            this.maxDebts = thresholdDebts + 2.0 * (2.0* stablePeriodMicros) / (this.stableIntervalMicros + increaseIntervalMicros);
            this.slope = (increaseIntervalMicros - stableIntervalMicros) / (maxDebts - thresholdDebts);

            if (oldMaxDebts == Double.POSITIVE_INFINITY) {
                owedDebts = 0.0;
            } else {
                owedDebts = (oldMaxDebts == 0.0) ? 0.0 : owedDebts * maxDebts / oldMaxDebts;
            }
        }

        long owedDebtsToWaitTime() {
            long micros = 0;
            if (owedDebts <= thresholdDebts) {
                micros = (long) (owedDebts * stableIntervalMicros);
            } else {
                double delta = owedDebts - thresholdDebts;
                micros = (long) (stablePeriodMicros + (2.0*stableIntervalMicros + delta*slope)*delta/2);
            }
            return micros;
        }
    }



    double owedDebts;

    double maxDebts;

    double stableIntervalMicros;

    SmoothTimeAbsorber(SleepingStopwatch stopwatch) {
        super(stopwatch);
    }

    @Override
    void doSetRate(double taskPreSecond) {
        double stableIntervalMicros = TimeUnit.SECONDS.toMicros(1) / taskPreSecond;
        this.stableIntervalMicros = stableIntervalMicros;
        doSetRate(taskPreSecond, stableIntervalMicros);
    }

    abstract void doSetRate(double taskPreSecond, double stableIntervalMicros);


    long adjustAndGetWaitLength(int debts) {
        update(debts);
        return owedDebtsToWaitTime();
    }

    abstract long owedDebtsToWaitTime();

    void update(int debts) {
        owedDebts = Math.min(maxDebts, owedDebts+debts);
    }

    @Override
    public double recover() {
        double owed = owedDebts;
        this.owedDebts = 0;
        return owed;
    }
}
