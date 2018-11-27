package com.vvwyy.cowpea.test.spi.car;

import com.vvwyy.cowpea.spi.ServiceCreationConfiguration;
import com.vvwyy.cowpea.spi.ServiceFactory;

public class TruckFactory implements ServiceFactory<Truck.Provider> {

    @Override
    public Truck.Provider create(ServiceCreationConfiguration<Truck.Provider> configuration) {
        return new Truck.Provider();
    }

    @Override
    public Class<? extends Truck.Provider> getServiceType() {
        return Truck.Provider.class;
    }
}
