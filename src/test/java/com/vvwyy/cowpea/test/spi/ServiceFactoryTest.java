package com.vvwyy.cowpea.test.spi;

import com.vvwyy.cowpea.spi.ServiceLocator;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ServiceFactoryTest {

    private ServiceLocator serviceLocator;

    @BeforeClass
    public static void beforeClass() {

    }

    @After
    public void after() {
        if (null != serviceLocator) {
            try {
                serviceLocator.stopAllServices();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        serviceLocator = null;
    }

    @Test
    public void testSimpleLoadWithoutStart() {
        serviceLocator = ServiceLocator.dependencySet()
                .with(Shape.Provider.class)
                .build();

        Shape shape = serviceLocator.getService(Square.Provider.class).create();
        Assert.assertNotNull(shape);
        Assert.assertEquals("Square", shape.draw());
        Assert.assertEquals(0, shape.area());
    }

    @Test
    public void testSimpleLoadWithStart() {
        serviceLocator = ServiceLocator.dependencySet()
                .with(Shape.Provider.class)
                .build();
        serviceLocator.startAllServices();

        Shape shape = serviceLocator.getService(Square.Provider.class).create();
        Assert.assertNotNull(shape);
        Assert.assertEquals("Square", shape.draw());
        Assert.assertEquals(100, shape.area());
    }

    @Test
    public void testLoadUnconfigService() {
        try {
            serviceLocator = ServiceLocator.dependencySet()
                    .with(GhostShape.Provider.class)
                    .build();
        } catch (IllegalStateException e) {
            Assert.assertTrue(true);
            return;
        }
        Assert.fail("Need to fail.");
    }

    @Test
    public void testGetUnconfigService() {
        serviceLocator = ServiceLocator.dependencySet()
                .with(Square.Provider.class)
                .build();

        Assert.assertEquals(false, serviceLocator.knowsServiceFor(GhostShape.Provider.class));
        Assert.assertEquals(true, serviceLocator.knowsServiceFor(Square.Provider.class));


        Shape.Provider shapeProvider = serviceLocator.getService(GhostShape.Provider.class);
        Assert.assertNull(shapeProvider);
    }



}
