package com.vvwyy.cowpea;

import com.google.common.util.concurrent.RateLimiter;
import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

public class TimeVortexTest {

    @Test
    public void testSimple() {
//        RateLimiter limiter = RateLimiter.create();
        TimeVortex vortex = TimeVortex.create(2);

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
