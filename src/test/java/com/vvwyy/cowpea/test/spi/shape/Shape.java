package com.vvwyy.cowpea.test.spi.shape;

import com.vvwyy.cowpea.spi.Service;

public interface Shape {

    default String draw(){
        return "Shape";
    }

    int area();

    interface Provider extends Service {
        Shape create();
    }
}
