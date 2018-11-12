package com.vvwyy.cowpea.demo.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class TargetOtherInterceptor implements MethodInterceptor {


    /**
     * 重写方法拦截在方法前和方法后加入业务
     * Object obj为目标对象
     * Method method为目标方法
     * Object[] params 为参数，
     * MethodProxy proxy CGlib方法代理对象
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {

        System.out.println("Other Pre handle");

        Object result = proxy.invokeSuper(obj, args);
        System.out.println("Invoke method["+proxy.getSuperName()+"]");

        System.out.println("Other Post handle");

        return result;
    }




}
