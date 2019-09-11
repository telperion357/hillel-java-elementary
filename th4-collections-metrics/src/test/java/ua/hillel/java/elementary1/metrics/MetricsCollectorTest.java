package ua.hillel.java.elementary1.metrics;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MetricsCollectorTest {

    @Mock
    private MetricsCollector collector;

    @Test
    public void testCounters() {
        doAnswer(i -> null).when(collector).incrementCounter(anyString(), anyInt());
        collector.incrementCounter("test", 1);
        verify(collector, times(1)).incrementCounter(anyString(), anyInt());
    }

    @Test
    public void testDuration() {
       long startTime = System.currentTimeMillis();
       /// TODO
       long time = System.currentTimeMillis() - startTime;
       collector.reportDuration("f-x", time, TimeUnit.MILLISECONDS);
    }


}