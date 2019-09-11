package ua.hillel.java.elementary1.proxy;

/**
 * Reverse proxy basic class which uses {@link Configuration} to start.
 */
public abstract class AbstractReverseProxy {
    private Configuration configuration;

    /**
     * Instantiates a new Abstract reverse proxy.
     *
     * @param configuration the configuration
     */
    public AbstractReverseProxy(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * Start application with configuration provided.
     */
    public abstract void start();

    /**
     * Stop application with configuration provided.
     */
    public abstract void stop();

    protected Configuration getConfiguration() {
        return configuration;
    }
}
