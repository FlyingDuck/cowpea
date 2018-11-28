package com.vvwyy.cowpea.test.spi.car;

import com.vvwyy.cowpea.spi.Service;

public interface Engine {

    int cylinderNum();

    interface Provider extends Service {

        Engine create();
    }

}
