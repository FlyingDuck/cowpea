package com.vvwyy.cowpea.demo;

import com.google.common.util.concurrent.RateLimiter;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

public class GuavaTest {

    @Test
    public void testRateLimit() {
        final RateLimiter limiter = RateLimiter.create(5);

        final int count = 10;
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(count);

        for (int i=0; i< count; i++) {
            final int interval = i;
            new Thread(new Runnable() {
                public void run() {
                    try {
                        try {
                            startGate.await();
                            Thread.sleep(100*interval);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (limiter.tryAcquire(1)) {
                            System.out.println(Thread.currentThread().getName() + "- Acquired");
                        } else {
                            System.out.println(Thread.currentThread().getName() + "- NotAcquired");
                        }
                    } finally {
                        endGate.countDown();
                    }
                }
            }).start();
        }
        startGate.countDown();

        try {
            endGate.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
