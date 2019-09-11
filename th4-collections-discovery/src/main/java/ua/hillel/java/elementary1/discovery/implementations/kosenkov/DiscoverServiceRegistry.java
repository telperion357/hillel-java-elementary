package ua.hillel.java.elementary1.discovery.implementations.kosenkov;

import ua.hillel.java.elementary1.discovery.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

// Vocabulary:
// Weighted random selection, uniform distribution, probability.

public class DiscoverServiceRegistry<T extends Service> implements RegistryService<T> {

    // The database to hold lists of service records, mapped by service names;
    private Map<String, List<Record>> registryMap;

    public DiscoverServiceRegistry() {
        registryMap = new HashMap<>();
    }


    // Bundles the service with ttl information and adds it to the list of services,
    // stored under the service name in the database.
    //
    @Override
    public void register(T service, long ttl, TimeUnit unit) {

        if (service == null) {
            throw new IllegalArgumentException("Service for registration can not be null");
        }
        if(ttl <= 0) {
            throw new IllegalArgumentException("TTL should be positive");
        }

        // Bundle the service into a record with ttl information.
        Record<T> record = new Record(service, ttl, unit);

        // Get the list of services with the given name from map, or create one
        // Add the record to the list.
        // Put the list back into the map.
        String serviceName = service.name();
        List<Record> recordList = registryMap.get(serviceName);
        if (recordList == null) {
            recordList = new ArrayList<>();
        }
        recordList.add(record);
        registryMap.put(serviceName, recordList);
    }

    // The implementation of weighted random selection of services
    // We get the list of services with the given name from the map.
    // On the first iteration over the list, we remove records with the expired services
    // and count the total weight sum and partial weight sums of service weights.
    // Then we generate a random number in the range [0, 1)*(total sum).
    // On the second iteration we return the first record with the partial sum
    // greater than the random number.
    //
    @Override
    public T discover(String name) {

        double weightSum = 0;
        double random;

        if (name == null) {
            throw new IllegalArgumentException("Name of service to discover can not be null");
        }

        // If there are no services for the given name, return null
        List<Record> recordList = registryMap.get(name);
        if (recordList == null || recordList.isEmpty()) {
            return null;
        }

        // First iteration.
        // Remove records with expired services and
        // count total weight sum and partial weight sums.
        Iterator<Record> recordItr = recordList.iterator();
        while (recordItr.hasNext()) {
            Record record = recordItr.next();
            if ( ! record.serviceIsExpired()) {
                weightSum += record.getService().weight();
                record.setPartialWeightSum(weightSum);
            } else {
                recordItr.remove();
            }
        }

        // Generate a random number.
        random = Math.random();

        // Second iteration.
        // Found the record with partial weight sum
        // greater than the normalized random number.
        for (Record record : recordList) {
            if (record.getPartialWeightSum() > random*weightSum) {
                // Update record registration time to current time
                // to implement LRU (least recently used) strategy
                record.updateRegistrationTime();
                return (T) record.getService();
            }
        }

        // If all services were removed as expired, return null
        return null;
    }

    // Returns the list of non-expired services under the given name.
    // If there are no registered services or all services have expired, returns the empty list.
    //
    @Override
    public Collection<T> discoverAll(String name) {
        List<T> serviceList = new ArrayList<>();
        // Get the list of services with the given name from the database.
        List<Record> recordList = registryMap.get(name);
        // Iterate through the list of records, remove records with expired services,
        // copy the valid services into the list of services and return it.
        Iterator<Record> recordItr = recordList.iterator();
        while (recordItr.hasNext()) {
            Record record = recordItr.next();
            if ( ! record.serviceIsExpired()) {
                serviceList.add((T) record.getService());
                // Update LRU information
                record.updateRegistrationTime();
            } else {
                recordItr.remove();
            }
        }
        return serviceList;
    }
}
