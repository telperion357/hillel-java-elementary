package ua.hillel.java.elementary1.discovery.implementations.kosenkov;

import org.junit.Before;
import org.junit.Test;
import ua.hillel.java.elementary1.discovery.Service;

import static org.junit.Assert.*;

public class WeightedServiceTest {

    private final double WEIGHT = 5.0;
    private final String NAME = "Test name";
    private Service service;

    @Before
    public void setUp() {
        service = new WeightedService(WEIGHT, NAME);
    }

    @Test
    public void weight() {
        assertEquals(WEIGHT, service.weight(), 1e-3);
    }

    @Test
    public void name() {
        assertEquals(NAME, service.name());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeWeight() {
        Service s = new WeightedService(-WEIGHT, NAME);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNull() {
        Service s = new WeightedService(WEIGHT, null);
    }


}