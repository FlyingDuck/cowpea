package com.vvwyy.cowpea.test.spi.car;

import com.vvwyy.cowpea.spi.Service;

public interface Wheel {

    int measure();

    interface Provider extends Service {
        Wheel create(int measure);
    }

}
