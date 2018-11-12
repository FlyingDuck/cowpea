package com.vvwyy.cowpea.demo.cglib;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class CglibTest {

    @Test
    public void testCallback() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Target.class);
        enhancer.setCallback(new TargetInterceptor());

        Target target = (Target) enhancer.create();

        String result = target.targetMethod();
        System.out.println(result);

    }

    @Test
    public void testFilter() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Target.class);
        enhancer.setCallbacks(new Callback[]{new TargetInterceptor(), new TargetOtherInterceptor()});
        enhancer.setCallbackFilter(new TargetCallbackFilter());

        Target target = (Target) enhancer.create();

        String result = target.targetMethod();
        System.out.println(result);

        result = target.nonTargetMethod();
        System.out.println(result);
    }

    @Test
    public void testLazyLoader() throws InterruptedException {
        LazyBean lazyBean = new LazyBean("lazy");


        System.out.println(lazyBean.getPropBean());
        System.out.println(lazyBean.getPropBean());
        System.out.println(lazyBean.getPropBean2());
        System.out.println(lazyBean.getPropBean2());

        /*System.out.println(lazyBean.getPropBean().getName());
        TimeUnit.SECONDS.sleep(1);
        System.out.println(lazyBean.getPropBean().getName());
        TimeUnit.SECONDS.sleep(1);
        System.out.println(lazyBean.getPropBean2().getName());
        TimeUnit.SECONDS.sleep(1);
        System.out.println(lazyBean.getPropBean2().getName());*/

    }
}