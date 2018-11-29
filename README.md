# Cowpea

A Development Kit for Java


## dependency

```
<dependency>
    <groupId>com.vvwyy</groupId>
    <artifactId>cowpea</artifactId>
    <version>0.0.5-BATE</version>
</dependency>

```

## Components

- TimeAbsorber
- SPI : ServiceFactory

### TimeAbsorber

```
// Create a SmoothConstant absorber with TPS 100
TimeAbsorber absorber = TimeAbsorber.create(100);
// Or Create a SmoothLinear absorber with TPS 100 and 1min stable period
TimeAbsorber absorber = TimeAbsorber.create(100, 60, TimeUnit.SECONDS);

// sleep
absorber.absorb();
// recover： clean the owed time
absorber.recover();
```
### SPI - ServiceFactory

init ServiceProvider `servicelocator`:
```
ServiceLocator serviceLocator = ServiceLocator.serviceFactoryLocatorBuilder()
                .with(Shape.Provider.class)
                .with(Auto.Provider.class)
                .build();

serviceLocator.startAllServices();
```

Get service:
```
Auto.Provider truckProvider = serviceLocator.getService(Truck.Provider.class); // Auto -> Truck
Shape shape = serviceLocator.getService(Square.Provider.class).create(); // Shape -> Square
Shape shape = serviceLocator.getService(Shape.Provider.class).create(); // Shape -> Square

Collection<Auto.Provider> autoProviders = serviceLocator.getServicesOfType(Auto.Provider.class); // <Car, Truck>
```


### SPI - Service

// todo doc


**@PluralService** 
Indicates that a Service subtype is permitted to have more than one concrete implementation registered with a ServiceProvider.

**@ServiceDependencies**
Annotation that allows a Service implementation to declare a dependency on other Services.

**@OptionalServiceDependencies**
Annotation that allows a Service implementation to declare an optional dependency on other Services.



