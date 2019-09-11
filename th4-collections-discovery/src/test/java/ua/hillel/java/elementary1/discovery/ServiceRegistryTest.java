package ua.hillel.java.elementary1.discovery;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.reflections.Reflections;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ServiceRegistryTest {
    private static final int RUN_NUM = 10000;
    private static final String SERVICE_NAME = "test";

    private Collection<Registry<TestService>> registries;

    @Before
    public void setUp() {
        registries = implementations();
    }

    @Test
    public void testWeights() {
        for (Registry<TestService> registry : registries) {
            registry.register(new TestService(0, 1, SERVICE_NAME), 1, TimeUnit.DAYS);
            registry.register(new TestService(1, 3, SERVICE_NAME), 1, TimeUnit.DAYS);
            registry.register(new TestService(2, 6, SERVICE_NAME), 1, TimeUnit.DAYS);
            //
            Map<TestService, Integer> count = new HashMap<>();
            for (int i = 0; i < RUN_NUM; i++) {
                TestService selected = registry.discover(SERVICE_NAME);
                Assert.assertNotNull("Failed on select " + SERVICE_NAME + " with " + registry.getClass(),
                        selected);

                Integer prev = count.get(selected);
                count.put(selected, prev == null ? 1 : prev + 1);
            }
            double sum = 0;
            for (TestService service : registry.discoverAll(SERVICE_NAME)) {
                sum += service.weight();
            }

            for (Map.Entry<TestService, Integer> entry : count.entrySet()) {
                double selected = entry.getValue() / (double) RUN_NUM;
                double expected = entry.getKey().weight() / sum;
                // 10% loss.
                Assert.assertEquals("Failed on weights '" + SERVICE_NAME + "' with " + registry.getClass(),
                        expected, selected, 0.1);
            }
        }
    }

    @Test
    public void testExpiration() {
        for (Registry<TestService> registry : registries) {
            registry.register(new TestService(0, 0, SERVICE_NAME), 1, TimeUnit.SECONDS);
        }
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }
        for (Registry<TestService> registry : registries) {
            TestService selected = registry.discover(SERVICE_NAME);
            Assert.assertNull("Failed to expire '" + SERVICE_NAME + "' with " + registry.getClass(),
                    selected);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> Collection<Registry<T>> implementations() {
        Reflections reflections = new Reflections(Registry.class.getPackage().getName() + ".implementations");
        return reflections.getSubTypesOf(Registry.class)
                .stream()
                .filter(c -> !c.isInterface())
                .map(cl -> {
                    try {
                        return (Registry<T>) cl.newInstance();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList());
    }


    private static class TestService implements Service {
        private int id;
        private double weight;
        private String name;

        TestService(int id, double weight, String name) {
            this.id = id;
            this.weight = weight;
            this.name = name;
        }

        @Override
        public double weight() {
            return weight;
        }

        @Override
        public String name() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TestService that = (TestService) o;
            return id == that.id &&
                    Double.compare(that.weight, weight) == 0 &&
                    Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, weight, name);
        }
    }
}