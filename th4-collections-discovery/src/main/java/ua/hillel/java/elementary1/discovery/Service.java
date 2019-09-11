package ua.hillel.java.elementary1.discovery;

/**
 * Service which used to register in service discovery.
 */
public interface Service {
    /**
     * Weight of current service : some number from 0 till +inf
     *
     * @return the weight of the current service.
     */
    double weight();

    /**
     * Name of the service used during discovery.
     *
     * @return the name of service.
     */
    String name();
}
