package ua.hillel.java.elementary1.proxy.implementation.kosenkov;

public class Endpoint {
    private String hostname;
    private int port;
    private double weight;

    public Endpoint(String hostname, int port, double weight) {
        this.hostname = hostname;
        this.port = port;
        this.weight = weight;
    }

    public String getHostname() {
        return hostname;
    }

    public int getPort() {
        return port;
    }

    public double getWeight() {
        return weight;
    }
}