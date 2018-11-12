package com.vvwyy.cowpea.demo.cglib;

import net.sf.cglib.proxy.Enhancer;

public class LazyBean {

    private String label;
    private PropBean propBean;
    private PropBean propBean2;

    public LazyBean(String label) {
        this.label = label;
        this.propBean = createBeanProp();
        this.propBean2 = createBeanPropDispatcher();
    }

    private PropBean createBeanProp() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(PropBean.class);
        return (PropBean) enhancer.create(PropBean.class, new PropBeanLazyLoader());
    }

    private PropBean createBeanPropDispatcher() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(PropBean.class);
        return (PropBean) enhancer.create(PropBean.class, new PropBeanDispatcher());
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public PropBean getPropBean() {
        return propBean;
    }

    public void setPropBean(PropBean propBean) {
        this.propBean = propBean;
    }

    public PropBean getPropBean2() {
        return propBean2;
    }

    public void setPropBean2(PropBean propBean2) {
        this.propBean2 = propBean2;
    }
}
