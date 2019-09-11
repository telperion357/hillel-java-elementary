package ua.hillel.java.elementary1.discovery.implementations.kosenkov;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ua.hillel.java.elementary1.discovery.Service;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class RecordTest {
    private final double WEIGHT = 5.0;
    private final String NAME = "Test name";
    private final Service SERVICE = new WeightedService(WEIGHT, NAME);
    private final long TTL = 1;
    private final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
    private Record<Service> record;

    @Before
    public void setUp() {
        record = new Record<Service>(SERVICE, TTL, TIME_UNIT);
    }

    @Test
    public void getService() {
        assertEquals(SERVICE, record.getService());
    }

    @Test
    public void getPartialWeightSum() {
        record.setPartialWeightSum(WEIGHT);
        assertEquals(WEIGHT, record.getPartialWeightSum(), 1e-03);
    }

    @Test
    public void serviceIsExpired() {
        assertEquals(false, record.serviceIsExpired());
        try {
            TIME_UNIT.sleep(TTL + 1);
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }
        assertEquals(true, record.serviceIsExpired());
    }

    @Test
    public void updateRegistrationTime() {
        assertEquals(false, record.serviceIsExpired());
        try {
            TIME_UNIT.sleep(TTL + 1);
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }
        assertEquals(true, record.serviceIsExpired());
        record.updateRegistrationTime();
        assertEquals(false, record.serviceIsExpired());
    }
}