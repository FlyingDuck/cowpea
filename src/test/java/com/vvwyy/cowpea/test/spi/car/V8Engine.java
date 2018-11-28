package com.vvwyy.cowpea.test.spi.car;

import com.vvwyy.cowpea.spi.Service;
import com.vvwyy.cowpea.spi.ServiceProvider;

public class V8Engine implements Engine {
    @Override
    public int cylinderNum() {
        return 8;
    }

    static class Provider implements Engine.Provider {

        @Override
        public Engine create() {
            return new V8Engine();
        }

        @Override
        public void start(ServiceProvider<Service> serviceProvider) {

        }

        @Override
        public void stop() {

        }
    }
}
