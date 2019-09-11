package ua.hillel.java.elementary1.discovery.implementations.kosenkov;

import ua.hillel.java.elementary1.discovery.Service;

import java.util.Objects;

public class WeightedService implements Service {

    private double weight;
    private String name;

    public WeightedService() {}


    public WeightedService(double weight, String name) {
        if (weight < 0) {
            throw new IllegalArgumentException("Weight should be positive or zero");
        }
        if (name == null) {
            throw new IllegalArgumentException("Service name can not be null");
        }
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
    public int hashCode() {
        return Objects.hash(weight, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeightedService that = (WeightedService) o;
        return Double.compare(that.weight, weight) == 0 &&
                Objects.equals(name, that.name);
    }
}
