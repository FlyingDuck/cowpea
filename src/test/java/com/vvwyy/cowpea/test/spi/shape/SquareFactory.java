package com.vvwyy.cowpea.test.spi.shape;


import com.vvwyy.cowpea.spi.ServiceCreationConfiguration;
import com.vvwyy.cowpea.spi.ServiceFactory;

public class SquareFactory implements ServiceFactory<Square.Provider> {
    @Override
    public Square.Provider create(ServiceCreationConfiguration<Square.Provider> configuration) {
        return new Square.Provider();
    }

    @Override
    public Class<? extends Square.Provider> getServiceType() {
        return Square.Provider.class;
    }
}
