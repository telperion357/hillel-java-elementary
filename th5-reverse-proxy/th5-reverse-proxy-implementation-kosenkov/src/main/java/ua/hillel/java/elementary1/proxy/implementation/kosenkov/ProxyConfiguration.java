package ua.hillel.java.elementary1.proxy.implementation.kosenkov;

import ua.hillel.java.elementary1.proxy.Configuration;

import java.util.List;

public class ProxyConfiguration implements Configuration {
    private List<Server> servers;

    public ProxyConfiguration(List<Server> servers) {
        this.servers = servers;
    }

    public List<Server> getConfig() {
        return servers;
    }
}
