package ua.hillel.java.elementary1.proxy;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * This is basic configuration builder used to configure
 * reverse proxy for your application.
 * <p>
 * Basic configuration consists of :
 * 1. Application - can hold 1 to N active servers.
 * 2. Server - listens port and host, provide ability to call endpoints.
 * 3. Endpoints - holds hostname, port and weight of endpoint to forward traffic.
 * 4. Endpoint can have health-check. Optional possibility to monitor failed endpoints.
 */
public abstract class AbstractConfigurationBuilder {

    protected static class ServerBuilder {
        public String hostname;
        public int port;
        // Failover mechanism configuration.
        public int retryCount = -1;
        public long retryMillis;
        public double backOffMultiplier = 1.0;
        // Limiting configuration
        public long requests = -1;
        public long periodMillis;
        //
        public List<EndpointBuilder> endpoints = new ArrayList<>();
        AbstractConfigurationBuilder builder;

        private ServerBuilder(AbstractConfigurationBuilder builder) {
            this.builder = builder;
        }

        public ServerBuilder setHostname(String hostname) {
            this.hostname = hostname;
            return this;
        }

        public ServerBuilder setPort(int port) {
            this.port = port;
            return this;
        }

        public ServerBuilder setRetryCount(int retryCount) {
            this.retryCount = retryCount;
            return this;
        }

        public ServerBuilder setRetryMillis(long retryMillis) {
            this.retryMillis = retryMillis;
            return this;
        }

        public ServerBuilder setBackOffMultiplier(double backOffMultiplier) {
            this.backOffMultiplier = backOffMultiplier;
            return this;
        }

        public ServerBuilder setRequests(long requests) {
            this.requests = requests;
            return this;
        }

        public ServerBuilder setPeriodMillis(long periodMillis) {
            this.periodMillis = periodMillis;
            return this;
        }

        public EndpointBuilder withEndpoint() {
            EndpointBuilder current = new EndpointBuilder(this);
            endpoints.add(current);
            return current;
        }

        public AbstractConfigurationBuilder and() {
            return builder;
        }

        private Configuration build() {
            return builder.build();
        }

        public ServerBuilder withServer() {
            return builder.withServer();
        }
    }

    protected static class EndpointBuilder {
        public String hostname;
        public int port;
        public double weight;
        // Health check configuration
        public Supplier<Boolean> healthCheck;
        public long healthCheckIntervalMillis;

        public ServerBuilder builder;

        public EndpointBuilder(ServerBuilder builder) {
            this.builder = builder;
        }

        public EndpointBuilder setHostname(String hostname) {
            this.hostname = hostname;
            return this;
        }

        public EndpointBuilder setPort(int port) {
            this.port = port;
            return this;
        }

        public EndpointBuilder setWeight(double weight) {
            this.weight = weight;
            return this;
        }

        public EndpointBuilder setHealthCheck(Supplier<Boolean> healthCheck) {
            this.healthCheck = healthCheck;
            return this;
        }

        public EndpointBuilder setHealthCheckIntervalMillis(long healthCheckIntervalMillis) {
            this.healthCheckIntervalMillis = healthCheckIntervalMillis;
            return this;
        }

        public ServerBuilder and() {
            return builder;
        }

        public Configuration build() {
            return builder.build();
        }
    }

    protected List<ServerBuilder> servers = new ArrayList<>();
    protected String configurationFile;

    public ServerBuilder withServer() {
        ServerBuilder builder = new ServerBuilder(this);
        servers.add(builder);
        return builder;
    }

    public AbstractConfigurationBuilder withConfigurationFile(String configurationFile) {
        this.configurationFile = configurationFile;
        return this;
    }

    /**
     * Build configuration of the reverse proxy with help of existed builder.
     *
     * @return the configuration
     */
    public abstract Configuration build();
}
