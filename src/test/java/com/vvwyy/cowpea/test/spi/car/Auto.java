package com.vvwyy.cowpea.test.spi.car;

import com.vvwyy.cowpea.spi.Service;

public interface Auto {

    void drive();

    int wheelNum();

    int wheelMeasure();


    interface Provider extends Service {
        Auto create();
    }
}
