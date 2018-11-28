package com.vvwyy.cowpea.test.spi.shape;

import com.vvwyy.cowpea.spi.Service;
import com.vvwyy.cowpea.spi.ServiceProvider;

public class Circle implements Shape {

    private int radius;

    public Circle(int radius) {
        this.radius = radius;
    }

    @Override
    public String draw() {
        return "Circle";
    }

    @Override
    public int area() {
        return (int) (2*Math.PI*radius*radius);
    }

    public static class Provider implements Shape.Provider {

        @Override
        public Shape create() {
            return new Circle(2);
        }

        @Override
        public void start(ServiceProvider<Service> serviceProvider) {

        }

        @Override
        public void stop() {

        }
    }
}
