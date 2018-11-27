package com.vvwyy.cowpea.test.spi.shape;


import com.vvwyy.cowpea.spi.Service;
import com.vvwyy.cowpea.spi.ServiceProvider;

public class Square implements Shape {

    private int len;

    public Square(int len) {
        this.len = len;
    }


    @Override
    public String draw() {
        return "Square";
    }

    @Override
    public int area() {
        return len*len;
    }

    public static class Provider implements Shape.Provider {
        private int len;

        @Override
        public void start(ServiceProvider<Service> serviceProvider) {
            // 进行简单初始化（无依赖服务）
            len = 10;
        }

        @Override
        public void stop() {

        }

        @Override
        public Shape create() {
            return new Square(len);
        }
    }

}
