package com.vvwyy.cowpea.test.spi.car;

import com.vvwyy.cowpea.spi.ServiceCreationConfiguration;
import com.vvwyy.cowpea.spi.ServiceFactory;

public class V8EngineFactory implements ServiceFactory<V8Engine.Provider> {

    @Override
    public V8Engine.Provider create(ServiceCreationConfiguration<V8Engine.Provider> configuration) {
        return new V8Engine.Provider();
    }

    @Override
    public Class<? extends V8Engine.Provider> getServiceType() {
        return V8Engine.Provider.class;
    }
}
