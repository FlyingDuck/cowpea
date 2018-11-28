package com.vvwyy.cowpea.test.spi.shape;

import com.vvwyy.cowpea.spi.ServiceCreationConfiguration;
import com.vvwyy.cowpea.spi.ServiceFactory;

public class CircleFactory implements ServiceFactory<Circle.Provider> {
    @Override
    public Circle.Provider create(ServiceCreationConfiguration<Circle.Provider> configuration) {
        return new Circle.Provider();
    }

    @Override
    public Class<? extends Circle.Provider> getServiceType() {
        return Circle.Provider.class;
    }
}
