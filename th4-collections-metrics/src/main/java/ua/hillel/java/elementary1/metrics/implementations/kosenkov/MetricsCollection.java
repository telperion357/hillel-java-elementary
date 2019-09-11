package ua.hillel.java.elementary1.metrics.implementations.kosenkov;

import com.google.gson.Gson;
import ua.hillel.java.elementary1.metrics.MetricsCollector;

import java.util.*;
import java.util.concurrent.TimeUnit;

// ***Metrics***
//
// Your task will be metrics counters
// Please implement ```ua.hillel.java.elementary1.metrics.MetricsCollector```
//
public class MetricsCollection implements MetricsCollector {

    // Stores counters values under counters names.
    private Map<String, Integer> counters;
    // Stores lists of durations in millis under timers names.
    private Map<String, List<Long>> timers;
    // Stores gauges values under gauges names.
    private Map<String, Long> gauges;

    public MetricsCollection() {
        counters = new HashMap<>();
        timers =   new HashMap<>();
        gauges =   new HashMap<>();
    }

// **Counters**
//
// Your metric collector should provide possibility to hold and increment counters.
// Counters are numbers witch can be incremented and decremented.

    /**
     * Adds the given delta to the counter with the given name.
     * If the counter does not exist,
     * creates one with the provided name and value.
     *
     * @param counter the counter name
     * @param delta   the delta to add
     */
    @Override
    public void incrementCounter(String counter, int delta) {
        // If there is no counter with such name, create one.
        if (counters.get(counter) == null) {
            counters.put(counter, delta);
            // Get the previous counter value from the map,
            // calculate the new value and store it back to the map.
        } else {
            Integer newValue = counters.get(counter) + delta;
            counters.put(counter, newValue);
        }
    }

    /**
     * Subtracts the given delta from the counter with the given name.
     * If the counter does not exist, creates one with the provided name and value.     *
     *
     * @param counter the counter name
     * @param delta   the delta to subtract
     */
    @Override
    public void decrementCounter(String counter, int delta) {
        // If there is no counter with such name, create one.
        if (counters.get(counter) == null) {
            counters.put(counter, -delta);
        } else {
            // Get the previous counter value from the map,
            // calculate the new value and store it back to the map.
            Integer newValue = counters.get(counter) - delta;
            counters.put(counter, newValue);
        }
    }

// **Durations**
//
// Your metric collector must be able to collect durations in time unit.
// Each timer should store all durations, reported to it.
// Please provide possibility to report min, max, avg time
// of each timer during toJson reporting.

    /**
     * Converts the provided duration to millis and stores it
     * to the list of durations under the timer name.
     *
     * @param timer    the timer name
     * @param duration the duration
     * @param unit     the time unit of duration
     */
    @Override
    public void reportDuration(String timer, long duration, TimeUnit unit) {
        List<Long> durations;
        if (timers.get(timer) == null) {
            // If there is no list for the given timer name, create one.
            durations = new ArrayList<>();
        } else {
            // Get the list of durations of the timer with the given name.
            durations = timers.get(timer);
        }
        // Convert duration to millis and add it to the list.
        durations.add(unit.toMillis(duration));
        // Store the list back into the map under the timer name.
        timers.put(timer, durations);
    }

// **Gauge**
//
// Gauge is simple key-value reference with set directly
// and get directly without any change.

    /**
     * Sets gauge value.
     *
     * @param name  the name of the gauge.
     * @param value the value.
     */
    @Override
    public void setGaugeValue(String name, long value) {
        gauges.put(name, value);
    }

    /**
     * Converts current metrics to Json string and clears values.
     *
     * @return the json formatted string of values.
     */
    @Override
    public String toJson() {
        Gson gson = new Gson();
        String result = gson.toJson(this);
        clearValues();
        return result;
    }

    /**
     * Clears the values of all metrics.
     *
     * Counters are set to zero.
     * Lists of timer values are cleared.
     * Gauges are set to zero.
     */
    private void clearValues() {
        counters.keySet().stream().forEach(key -> counters.put(key, 0));
        timers.keySet().stream().forEach(key -> timers.get(key).clear());
        gauges.keySet().stream().forEach(key -> gauges.put(key, 0L));
    }
}
