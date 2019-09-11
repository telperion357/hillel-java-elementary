package ua.hillel.java.elementary1.metrics;

import java.util.concurrent.TimeUnit;

/**
 * MetricsCollector class is responsible for collection of the metrics: Counters, Durations, Gauges It is also possible
 * to convert metrics into Json string representation.
 */
public interface MetricsCollector {

    /**
     * Increment counter for given name.
     *
     * @param counter the counter
     * @param delta   the delta
     */
    void incrementCounter(String counter, int delta);

    /**
     * Decrement counter for given name.
     *
     * @param counter the counter
     * @param delta   the delta
     */
    void decrementCounter(String counter, int delta);

    /**
     * Report duration in some unit of time.
     *
     * @param timer    the time
     * @param duration the duration
     * @param unit     the unit
     */
    void reportDuration(String timer, long duration, TimeUnit unit);

    /**
     * Sets gauge value.
     *
     * @param name  the name of the gauge.
     * @param value the value
     */
    void setGaugeValue(String name, long value);

    /**
     * Converts current metrics to Json string and clear values.
     *
     * @return the json formatted string of values.
     */
    String toJson();
}
