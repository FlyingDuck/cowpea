package com.vvwyy.cowpea;

import org.junit.Test;

import java.time.LocalDateTime;

public class TimeAbsorberTest {

    @Test
    public void testSimple() {
//        RateLimiter limiter = RateLimiter.create();
        TimeAbsorber vortex = TimeAbsorber.create(2);

        for (int i=0; i<10; i++) {
            if (i%2 == 0) { // 偶数代表成功
//                vortex.repay();
            } else {

            }
            System.out.println(i+":" + LocalDateTime.now());
            vortex.owe();
            System.out.println(i+":" + LocalDateTime.now());
        }
    }
}
