package com.vvwyy.cowpea.test.spi.car;

import com.vvwyy.cowpea.spi.Service;

public interface Auto {

    void drive();

    int wheelNum();

    int wheelMeasure();

    Engine getEngine();

    interface Provider extends Service {
        Auto create();
    }
}
