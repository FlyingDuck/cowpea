package com.vvwyy.cowpea.test.spi.car;

import com.vvwyy.cowpea.spi.Service;
import com.vvwyy.cowpea.spi.ServiceProvider;

public class RubberWheel implements Wheel {

    private int measure;

    public RubberWheel(int measure) {
        this.measure = measure;
    }

    @Override
    public int measure() {
        return measure;
    }

    public static class Provider implements Wheel.Provider {

        @Override
        public Wheel create(int measure) {
            return new RubberWheel(measure);
        }

        @Override
        public void start(ServiceProvider<Service> serviceProvider) {

        }

        @Override
        public void stop() {

        }
    }

}
