package com.vvwyy.cowpea.demo.cglib;

import net.sf.cglib.proxy.CallbackFilter;

import java.lang.reflect.Method;

public class TargetCallbackFilter implements CallbackFilter {


    @Override
    public int accept(Method method) {
        String name = method.getName();
        if (name!=null && name.contains("non")) {
            return 1;
        }
        return 0;
    }
}
