package ua.hillel.java.elementary1.proxy.implementation.kosenkov;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Arrays;
import java.util.List;

public class Server {
    /**
     * the server hostname
     */
    private String hostname;

    /**
     * the server port
     */
    private int port;

    /**
     * The list of server endpoints, to which the traffic is redirected.
     */
    private List<Endpoint> endpoints;

    /**
     * Auxiliary array of endpoint partial weight sums
     * for random weighted selection.
     */
    private double[] partWeightSums;

    /**
     * Total endpoint weight sum.
     */
    private double weightSum;

    public Server(String hostname, int port, List<Endpoint> endpoints) {
        this.hostname = hostname;
        this.port = port;
        this.endpoints = endpoints;
    }

    public SocketAddress getServerAddress() {
        return new InetSocketAddress(hostname, port);
    }

    /**
     * @return the list of endpoints for this server.
     */
    public List<Endpoint> getEndpoints() {
        return endpoints;
    }

    /**
     * Selects the endpoint (by the random weighted random selection)
     * from the list of this server endpoints.
     * @return the socket address of the selected endpoint.
     */
    public SocketAddress selectEndpoint() {
        Endpoint endpoint = randomWeightedSelect();
        return new InetSocketAddress(endpoint.getHostname(), endpoint.getPort());
    }


    /**
     * Selects the endpoint (by the random weighted random selection)
     * from the list of this server endpoints.
     * @return The selected endpoint.
     */
    private Endpoint randomWeightedSelect() {
        double random = Math.random()*weightSum;
        int index = Arrays.binarySearch(partWeightSums, random);
        if (index < 0) {
            index = Math.abs(index + 1);
        }
        return this.endpoints.get(index);
    }

    /**
     * Calculates the array of partial sums of endpoint weights.
     */
    private void calculatePartSum() {
        int size = endpoints.size();
        double sum = 0;
        partWeightSums = new double[size];
        for (int i = 0; i < size; i++) {
            sum += endpoints.get(i).getWeight();
            partWeightSums[i] = sum;
        }
        weightSum = sum;
    }
}
