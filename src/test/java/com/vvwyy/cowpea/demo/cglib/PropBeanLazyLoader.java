package com.vvwyy.cowpea.demo.cglib;

import net.sf.cglib.proxy.LazyLoader;

public class PropBeanLazyLoader implements LazyLoader {

    @Override
    public Object loadObject() throws Exception {
        PropBean propBean = new PropBean();
        propBean.setName("Lazy loader bean:" + System.currentTimeMillis());
        return propBean;
    }
}
