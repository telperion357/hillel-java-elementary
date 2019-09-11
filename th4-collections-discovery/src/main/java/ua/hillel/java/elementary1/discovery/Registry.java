package ua.hillel.java.elementary1.discovery;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * Registry used for managing entries.
 *
 * @param <S> the type parameter
 */
public interface Registry<S> {

    /**
     * Register service with name in service registry for some period of time.
     *
     * @param service the service to register.
     * @param ttl     the time quantity for service to live.
     * @param unit    the unit of time used for service ttl (time-to-live) time
     */
    void register(S service, long ttl, TimeUnit unit);

    /**
     * Discover valid of service with provided name.
     * Valid service for current call must count weight() of service during the call.
     *
     * @param name the name of the service.
     * @return the collection of services with given name.
     */
    S discover(String name);

    /**
     * Discover collection of services with provided name.
     *
     * @param name the name of the service.
     * @return the collection of services with given name.
     */
    Collection<S> discoverAll(String name);
}
