package com.vvwyy.cowpea.demo;

import org.junit.Test;

import java.util.concurrent.ThreadFactory;

public class BaseJavaTest {


    @Test
    public void test1() {
        final int COUNT_BITS = Integer.SIZE - 3;
        System.out.println("COUNT_BITS = " + COUNT_BITS);

        final int RUNNING    = -1 << COUNT_BITS;
        final int SHUTDOWN   =  0 << COUNT_BITS;
        final int STOP       =  1 << COUNT_BITS;
        final int TIDYING    =  2 << COUNT_BITS;
        final int TERMINATED =  3 << COUNT_BITS;
        System.out.println(Integer.toBinaryString(-1));
        System.out.println(Integer.toBinaryString(RUNNING));
        System.out.println(Integer.toBinaryString(SHUTDOWN));
        System.out.println(Integer.toBinaryString(STOP));
        System.out.println(Integer.toBinaryString(TIDYING));
        System.out.println(Integer.toBinaryString(TERMINATED));


        final int CAPACITY   = (1 << COUNT_BITS) - 1;
        System.out.println("CAPACITY: \n" + Integer.toBinaryString(CAPACITY));
        System.out.println("~CAPACITY: \n" + Integer.toBinaryString(~CAPACITY));
    }

    @Test
    public void test2() {
        int outer = 0;
        retry:
        for (;;) {
            System.out.println("Loop-Outer: " + (++outer));

            int inner = 0;
            for (;;) {
                System.out.println("Loop-Inner: " + (++inner));
                if (outer == 5)
                    break retry;
                if (inner == 3)
                    continue retry;
            }
        }
    }

}
