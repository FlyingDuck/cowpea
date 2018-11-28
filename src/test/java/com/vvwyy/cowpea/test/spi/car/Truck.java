package com.vvwyy.cowpea.test.spi.car;

import com.vvwyy.cowpea.spi.Service;
import com.vvwyy.cowpea.spi.ServiceDependencies;
import com.vvwyy.cowpea.spi.ServiceProvider;

import java.util.ArrayList;
import java.util.List;

public class Truck implements Auto {

    private List<Wheel> wheels;
    private Engine engine;

    public Truck(List<Wheel> wheels, Engine engine) {
        this.wheels = wheels;
        this.engine = engine;
    }


    @Override
    public void drive() {
        System.out.println("Truck driving");
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

    @ServiceDependencies({Wheel.Provider.class, Engine.Provider.class})
    public static class Provider implements Auto.Provider {
        private ServiceProvider<Service> serviceProvider;

        @Override
        public Auto create() {
            Wheel.Provider wheelProvider = serviceProvider.getService(Wheel.Provider.class);
            List<Wheel> wheels = new ArrayList<>(8);
            wheels.add(wheelProvider.create(30));
            wheels.add(wheelProvider.create(30));
            wheels.add(wheelProvider.create(30));
            wheels.add(wheelProvider.create(30));
            wheels.add(wheelProvider.create(30));
            wheels.add(wheelProvider.create(30));
            wheels.add(wheelProvider.create(30));
            wheels.add(wheelProvider.create(30));

            Engine.Provider engineProvider = serviceProvider.getService(Engine.Provider.class);
            Engine engine = engineProvider.create();

            Truck truck = new Truck(wheels, engine);

            return truck;
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
