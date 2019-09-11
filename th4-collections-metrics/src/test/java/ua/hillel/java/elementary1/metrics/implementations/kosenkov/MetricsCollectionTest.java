package ua.hillel.java.elementary1.metrics.implementations.kosenkov;

import org.junit.Test;
import ua.hillel.java.elementary1.metrics.MetricsCollector;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class MetricsCollectionTest {

    // All methods are tested in one,
    // because there is only one method to read metrics values.
    @Test
    public void toJson() {
        MetricsCollector metrCol = new MetricsCollection();
        // Set some values of counter, timer and gauge.
        metrCol.incrementCounter("counter1", 20);
        metrCol.decrementCounter("counter1", 10);
        metrCol.reportDuration("timer1",5, TimeUnit.SECONDS);
        metrCol.reportDuration("timer1",3, TimeUnit.HOURS);
        metrCol.setGaugeValue("gauge1", 7);
        // Check that toJason method generates json string with the set values.
        assertEquals("{\"counters\":{\"counter1\":10}," +
                        "\"timers\":{\"timer1\":[5000,10800000]}," +
                        "\"gauges\":{\"gauge1\":7}}",
                metrCol.toJson());
        // Check that toJason() method clears all values
        assertEquals("{\"counters\":{\"counter1\":0}," +
                        "\"timers\":{\"timer1\":[]}," +
                        "\"gauges\":{\"gauge1\":0}}",
                metrCol.toJson());
    }
}