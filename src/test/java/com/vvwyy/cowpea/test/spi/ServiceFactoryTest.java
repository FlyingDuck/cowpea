package com.vvwyy.cowpea.test.spi;

import com.vvwyy.cowpea.spi.ServiceLocator;
import com.vvwyy.cowpea.test.spi.car.Auto;
import com.vvwyy.cowpea.test.spi.car.Car;
import com.vvwyy.cowpea.test.spi.car.Truck;
import com.vvwyy.cowpea.test.spi.shape.Circle;
import com.vvwyy.cowpea.test.spi.shape.GhostShape;
import com.vvwyy.cowpea.test.spi.shape.Shape;
import com.vvwyy.cowpea.test.spi.shape.Square;
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
                .with(Square.Provider.class)
                .build();

        Shape shape = serviceLocator.getService(Square.Provider.class).create();
        Assert.assertNotNull(shape);
        Assert.assertEquals("Square", shape.draw());
        Assert.assertEquals(0, shape.area());
    }

    @Test
    public void testSimpleLoadWithStart() {
        serviceLocator = ServiceLocator.dependencySet()
                .with(Square.Provider.class)
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

    @Test
    public void testNoconfigDependencyService() {
        serviceLocator = ServiceLocator.dependencySet()
                .with(Square.Provider.class)
                .with(Car.Provider.class)
                .build();

        serviceLocator.startAllServices();

        Car.Provider carProvider = serviceLocator.getService(Car.Provider.class);
        Assert.assertNotNull(carProvider);

        try {
            Auto auto = carProvider.create();
        } catch (NullPointerException e) {
            Assert.assertTrue(true);
            return;
        }
        Assert.fail("Need to fail.");
    }

    @Test
    public void testConfigDependencyService() {
        serviceLocator = ServiceLocator.dependencySet()
                .with(Square.Provider.class)
                //.with(Car.Provider.class)
                .with(Auto.Provider.class)
                .build();

        serviceLocator.startAllServices();

        Truck.Provider truckProvider = serviceLocator.getService(Truck.Provider.class);
        Assert.assertNotNull(truckProvider);

        Auto auto = truckProvider.create();
        Assert.assertEquals(30, auto.wheelMeasure());
        Assert.assertEquals(8, auto.wheelNum());
    }

    @Test
    public void testConfigMultiDependencyService() {
        serviceLocator = ServiceLocator.dependencySet()
                .with(Square.Provider.class)
                .with(Truck.Provider.class)
                .build();

        serviceLocator.startAllServices();

        Truck.Provider truckProvider = serviceLocator.getService(Truck.Provider.class);
        Assert.assertNotNull(truckProvider);

        Auto auto = truckProvider.create();
        Assert.assertEquals(30, auto.wheelMeasure());
        Assert.assertEquals(8, auto.wheelNum());
        Assert.assertEquals(8, auto.getEngine().cylinderNum());
    }

    @Test
    public void testPluralDependencyService() {
        serviceLocator = ServiceLocator.dependencySet()
                .with(Square.Provider.class)
//                .with(Truck.Provider.class)
//                .with(Car.Provider.class)
                .with(Auto.Provider.class)
                .build();

        serviceLocator.startAllServices();

        Truck.Provider truckProvider = serviceLocator.getService(Truck.Provider.class);
        Assert.assertNotNull(truckProvider);

        Car.Provider carProvider = serviceLocator.getService(Car.Provider.class);
        Assert.assertNotNull(carProvider);
    }

    @Test
    public void testPluralDependencyWithoutAnnotation() {
        try {
            serviceLocator = ServiceLocator.dependencySet()
                    .with(Square.Provider.class)
                    .with(Circle.Provider.class)
                    .build();
        } catch (IllegalStateException e) {
            Assert.assertTrue(true);
            return;
        }
        Assert.fail("Need to fail.");
    }



}
