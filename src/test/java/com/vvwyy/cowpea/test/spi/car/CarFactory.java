package com.vvwyy.cowpea.test.spi.car;

import com.vvwyy.cowpea.spi.ServiceCreationConfiguration;
import com.vvwyy.cowpea.spi.ServiceFactory;

public class CarFactory implements ServiceFactory<Car.Provider> {

    @Override
    public Car.Provider create(ServiceCreationConfiguration<Car.Provider> configuration) {
        return new Car.Provider();
    }

    @Override
    public Class<? extends Car.Provider> getServiceType() {
        return Car.Provider.class;
    }
}
