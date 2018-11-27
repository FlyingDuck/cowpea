package com.vvwyy.cowpea.test.spi.car;

import com.vvwyy.cowpea.spi.ServiceCreationConfiguration;
import com.vvwyy.cowpea.spi.ServiceFactory;

public class RubberWheelFactory implements ServiceFactory<RubberWheel.Provider> {

    @Override
    public RubberWheel.Provider create(ServiceCreationConfiguration<RubberWheel.Provider> configuration) {
        return new RubberWheel.Provider();
    }

    @Override
    public Class<? extends RubberWheel.Provider> getServiceType() {
        return RubberWheel.Provider.class;
    }
}
