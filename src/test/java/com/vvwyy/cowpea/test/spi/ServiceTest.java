package com.vvwyy.cowpea.test.spi;

import com.vvwyy.cowpea.spi.ServiceLocator;
import com.vvwyy.cowpea.test.spi.car.Auto;
import com.vvwyy.cowpea.test.spi.car.Car;
import com.vvwyy.cowpea.test.spi.car.Truck;
import com.vvwyy.cowpea.test.spi.shape.Shape;
import com.vvwyy.cowpea.test.spi.shape.Square;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collection;

public class ServiceTest {

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
    public void testSimpleLoad() {
        serviceLocator = ServiceLocator.serviceLocatorBuilder()
                .with(Shape.Provider.class)
                .build();

        serviceLocator.startAllServices();

        Shape.Provider squareProvider = serviceLocator.getService(Square.Provider.class);
        Assert.assertNotNull(squareProvider);

        Shape square = squareProvider.create();
        Assert.assertNotNull(square);

        Assert.assertEquals("Square", square.draw());
        Assert.assertEquals(100, square.area());

    }

    @Test
    public void testPluralLoad() {
        serviceLocator = ServiceLocator.serviceLocatorBuilder()
                .with(Shape.Provider.class)
                .with(Auto.Provider.class)
                .build();

        serviceLocator.startAllServices();

        Auto.Provider carProvider = serviceLocator.getService(Car.Provider.class);
        Assert.assertNotNull(carProvider);

        Auto.Provider truckProvider = serviceLocator.getService(Truck.Provider.class);
        Assert.assertNotNull(truckProvider);
    }

    @Test
    public void testPluralLoad2() {
        serviceLocator = ServiceLocator.serviceLocatorBuilder()
                .with(Shape.Provider.class)
                .with(Auto.Provider.class)
                .build();

        serviceLocator.startAllServices();

        Collection<Auto.Provider> autoProviders = serviceLocator.getServicesOfType(Auto.Provider.class);
        Assert.assertNotNull(autoProviders);
        Assert.assertEquals(2, autoProviders.size());

        for (Auto.Provider provider : autoProviders) {
            Auto auto = provider.create();
            auto.drive();
        }
    }

}
