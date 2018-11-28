package com.vvwyy.cowpea.test.spi.car;

import com.vvwyy.cowpea.spi.Service;
import com.vvwyy.cowpea.spi.ServiceProvider;

import java.util.ArrayList;
import java.util.List;

public class Car implements Auto {

    private List<Wheel> wheels;
    private Engine engine;

    public Car(List<Wheel> wheels, Engine engine) {
        this.wheels = wheels;
        this.engine = engine;
    }

    @Override
    public void drive() {
        System.out.println("Car driving...");
    }

    @Override
    public int wheelNum() {
        return wheels.size();
    }

    @Override
    public int wheelMeasure() {
        return wheels.get(0).measure();
    }

    @Override
    public Engine getEngine() {
        return engine;
    }

    public static class Provider implements Auto.Provider {
        private ServiceProvider<Service> serviceProvider;

        @Override
        public Auto create() {
            Wheel.Provider wheelProvider = serviceProvider.getService(Wheel.Provider.class);
            List<Wheel> wheels = new ArrayList<>(4);
            wheels.add(wheelProvider.create(20));
            wheels.add(wheelProvider.create(20));
            wheels.add(wheelProvider.create(20));
            wheels.add(wheelProvider.create(20));

            Car car = new Car(wheels, null);
            return car;
        }

        @Override
        public void start(ServiceProvider<Service> serviceProvider) {
            this.serviceProvider = serviceProvider;
        }

        @Override
        public void stop() {

        }
    }
}
