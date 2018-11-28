package com.vvwyy.cowpea.test;

import com.vvwyy.cowpea.TimeAbsorber;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class TimeAbsorberTest {

    @Test
    public void testSimple() {
        TimeAbsorber absorber = TimeAbsorber.create(2);
//        TimeAbsorber absorber = TimeAbsorber.create(2, 60, TimeUnit.SECONDS);

        for (int i=0; i<10; i++) {

            if (i == 5)
                absorber.recover();
            long start = System.currentTimeMillis();
            double absorb = absorber.absorb();
            System.out.println("count:"+ i +" sleep = " + (System.currentTimeMillis()-start) + " absorb="+absorb);
        }
    }

}
