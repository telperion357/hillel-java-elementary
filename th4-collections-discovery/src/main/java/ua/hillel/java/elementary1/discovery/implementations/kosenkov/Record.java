package ua.hillel.java.elementary1.discovery.implementations.kosenkov;

import ua.hillel.java.elementary1.discovery.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

// A wrapper class to bundle service with registration time and ttl information
//
public class Record<T extends Service> {

        private T service;
        private double partialWeightSum;
        private long registrationTimeMillis;
        private long ttl;
        private TimeUnit ttlUnit;

        public Record() {};

        public Record(T service, long ttl, TimeUnit ttlUnit) {
            if (service == null) {
                throw new IllegalArgumentException("Service to record can not be null");
            }
            this.service = service;
            this.ttl = ttl;
            this.ttlUnit = ttlUnit;
            registrationTimeMillis = System.currentTimeMillis();
        }

    public T getService() {
        return service;
    }

    public double getPartialWeightSum() {
        return partialWeightSum;
    }

    public void setPartialWeightSum(double partialWeightSum) {
        this.partialWeightSum = partialWeightSum;
    }

    // TTL implementation
    public boolean serviceIsExpired() {
        long millisPassed = System.currentTimeMillis() - registrationTimeMillis;
        long ttlMillis = ttlUnit.toMillis(ttl);
        return millisPassed > ttlMillis;
    }

    // LRU (least recently used) implementation;
    public void updateRegistrationTime() {
        registrationTimeMillis = System.currentTimeMillis();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Record record = (Record) o;
        return Double.compare(record.partialWeightSum, partialWeightSum) == 0 &&
                registrationTimeMillis == record.registrationTimeMillis &&
                ttl == record.ttl &&
                Objects.equals(service, record.service) &&
                ttlUnit == record.ttlUnit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(service, registrationTimeMillis, ttl, ttlUnit);
    }
}