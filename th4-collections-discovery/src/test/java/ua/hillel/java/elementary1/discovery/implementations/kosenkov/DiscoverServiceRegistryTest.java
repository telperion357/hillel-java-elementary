package ua.hillel.java.elementary1.discovery.implementations.kosenkov;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class DiscoverServiceRegistryTest {
    private  final int SAMPLE_NUM = 1000000;
    private final double WEIGHT = 5.0;
    private final String NAME = "Test name";
    private final long TTL = 1;
    private final TimeUnit TIME_UNIT = TimeUnit.HOURS;
    private WeightedService service;
    private DiscoverServiceRegistry<WeightedService> registry;

    @Before
    public void setUp() {
        service = new WeightedService(WEIGHT, NAME);
        registry = new DiscoverServiceRegistry<>();
    }

    // Test registration and discovery functionality
    @Test
    public void register() {
        registry.register(service, TTL, TIME_UNIT);
        assertEquals(service, registry.discover(NAME));
    }

    // Null service arg is not allowed
    @Test(expected = IllegalArgumentException.class)
    public void registerNullService() {
        registry.register(null, TTL, TIME_UNIT);
    }

    // TTL should be positive
    @Test(expected = IllegalArgumentException.class)
    public void registerNegativeTTL() {
        registry.register(service, -1, TIME_UNIT);
    }

    // Test service random sampling by weight
    @Test
    public void discoverByWeight() {
        // Register services with varying weights.
        WeightedService service1 = new WeightedService(WEIGHT*1, NAME);
        WeightedService service2 = new WeightedService(WEIGHT*2, NAME);
        WeightedService service3 = new WeightedService(WEIGHT*3, NAME);
        registry.register(service1, TTL, TIME_UNIT);
        registry.register(service2, TTL, TIME_UNIT);
        registry.register(service3, TTL, TIME_UNIT);

        // Calculate the actual probability of registered services occurrence over the SAMPLE_NUM calls.
        double[] counters = new double[3];
        WeightedService service;
        for (int i = 0; i < SAMPLE_NUM; i++) {
            service = registry.discover(NAME);
            if (service.equals(service1)) {
                counters[0] += 1;
            }
            if (service.equals(service2)) {
                counters[1] += 1;
            }
            if (service.equals(service3)) {
                counters[2] += 1;
            }
        }
        for (int i = 0; i < 3; i++) {
            counters[i] = counters[i]/SAMPLE_NUM;
        }
        // Calculate the expected probability of registered services occurrence.
        double weightSum = WEIGHT*1 + WEIGHT*2 + WEIGHT*3;
        double[] expected = new double[] {WEIGHT*1/weightSum, WEIGHT*2/weightSum, WEIGHT*3/weightSum};

//        System.out.println(Arrays.toString(counters));
//        System.out.println(Arrays.toString(expected));

        // Compare expected and actual probabilities.
        assertArrayEquals(expected, counters, 0.01);
    }

    // Test discoverAll functionality
    @Test
    public void discoverAll() {
        // Register 3 services for 1 hour.
        WeightedService service1 = new WeightedService(WEIGHT*1, NAME);
        WeightedService service2 = new WeightedService(WEIGHT*2, NAME);
        WeightedService service3 = new WeightedService(WEIGHT*3, NAME);
        registry.register(service1, TTL, TIME_UNIT);
        registry.register(service2, TTL, TIME_UNIT);
        registry.register(service3, TTL, TIME_UNIT);

        assertArrayEquals(new WeightedService[] {service1, service2, service3}, registry.discoverAll(NAME).toArray());
    }

    // Test expired services are removed by discover method.
    @Test
    public void discoverExpiration() {
        // Register 3 services for 1 second.
        WeightedService service1 = new WeightedService(WEIGHT*1, NAME);
        WeightedService service2 = new WeightedService(WEIGHT*2, NAME);
        WeightedService service3 = new WeightedService(WEIGHT*3, NAME);
        registry.register(service1, 1, TimeUnit.SECONDS);
        registry.register(service2, 1, TimeUnit.SECONDS);
        registry.register(service3, 1, TimeUnit.SECONDS);
        // assure services were registered
        assertEquals(3, registry.discoverAll(NAME).size());
        assertNotNull(registry.discover(NAME));
        // wait 2 seconds
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }
        // All services expired and was removed by discover method
        assertNull(registry.discover(NAME));
    }

    // Test expired services are removed by discoverAll method.
    @Test
    public void discoverAllExpiration() {
        // Register 3 services for 1 second.
        WeightedService service1 = new WeightedService(WEIGHT*1, NAME);
        WeightedService service2 = new WeightedService(WEIGHT*2, NAME);
        WeightedService service3 = new WeightedService(WEIGHT*3, NAME);
        registry.register(service1, 1, TimeUnit.SECONDS);
        registry.register(service2, 1, TimeUnit.SECONDS);
        registry.register(service3, 1, TimeUnit.SECONDS);
        // assure services were registered
        assertArrayEquals(new WeightedService[] {service1, service2, service3}, registry.discoverAll(NAME).toArray());

        // wait 2 seconds
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }
        // All services expired and was removed by discoverAll method
        assertEquals(0, registry.discoverAll(NAME).size());
    }

    // Test least recently used
    @Test
    public void LRU() {
        long currentTime;
        long prevTime;
        long ttl = 1000;
        // Register two services with different weights.
        WeightedService service1 = new WeightedService(WEIGHT*1, NAME);
        WeightedService service2 = new WeightedService(WEIGHT*2, NAME);
        registry.register(service1, ttl, TimeUnit.MILLISECONDS);
        registry.register(service2, ttl, TimeUnit.MILLISECONDS);
        // Assure services were registered
        assertEquals(2, registry.discoverAll(NAME).size());
        assertArrayEquals(new WeightedService[] {service1, service2}, registry.discoverAll(NAME).toArray());

        // Call discover several times with the period ttl/2;
        // This assures, that one of the the services will expire and will be removed,
        // and another service will live longer than its initial ttl.
        prevTime = System.currentTimeMillis();
//        System.out.println(prevTime);
        for (int i = 0; i < 10; i++) {
            try {
                TimeUnit.MILLISECONDS.sleep(ttl/2);
            } catch (InterruptedException e) {
                Assert.fail(e.toString());
            }
            service = registry.discover(NAME);
            currentTime = System.currentTimeMillis();

//            System.out.println("i :" + i);
//            System.out.println(currentTime - prevTime);

            // Assure that "sleep" time does not exceed ttl time
            assertEquals(true, (ttl/2 + ttl/4) > (currentTime - prevTime));
            prevTime = currentTime;
        }
        // Assert only one of the services lived longer
        assertEquals(1, registry.discoverAll(NAME).size());
    }

}