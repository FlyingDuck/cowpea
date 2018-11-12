package com.vvwyy.cowpea.demo.cglib;

import net.sf.cglib.proxy.Dispatcher;

public class PropBeanDispatcher implements Dispatcher {

    @Override
    public Object loadObject() throws Exception {
        PropBean propBean = new PropBean();
        propBean.setName("Dispatcher bean:"+System.currentTimeMillis());
        return propBean;
    }
}
