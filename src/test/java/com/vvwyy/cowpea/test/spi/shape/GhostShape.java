package com.vvwyy.cowpea.test.spi.shape;

import com.vvwyy.cowpea.spi.Service;
import com.vvwyy.cowpea.spi.ServiceProvider;

public class GhostShape implements Shape {
    @Override
    public String draw() {
        return "GhostShape";
    }

    @Override
    public int area() {
        return 0;
    }

    public class Provider implements Shape.Provider {

        @Override
        public Shape create() {
            return null;
        }

        @Override
        public void start(ServiceProvider<Service> serviceProvider) {

        }

        @Override
        public void stop() {

        }
    }
}
