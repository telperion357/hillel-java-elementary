package ua.hillel.java.elementary1.proxy.implementation.kosenkov;

import com.google.gson.Gson;
import ua.hillel.java.elementary1.proxy.AbstractConfigurationBuilder;
import ua.hillel.java.elementary1.proxy.Configuration;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigurationBuilder extends AbstractConfigurationBuilder {

    @Override
    public Configuration build() {
        // Build the List of Servers from the list of ServerBuilders.
        List<Server> servers = new ArrayList<>();
        for (ServerBuilder serverBuilder : this.servers) {
            List<Endpoint> endpoints = new ArrayList<>();
            for (EndpointBuilder endpointBuilder : serverBuilder.endpoints) {
                Endpoint endpoint = new Endpoint(
                        endpointBuilder.hostname,
                        endpointBuilder.port,
                        endpointBuilder.weight
                );
                endpoints.add(endpoint);
            }
            Server server = new Server(
                    serverBuilder.hostname,
                    serverBuilder.port,
                    endpoints
            );
            servers.add(server);
        }
        return new ProxyConfiguration(servers);
    }

    @Override
    public AbstractConfigurationBuilder withConfigurationFile(String configurationFile) {
        StringBuilder configString = new StringBuilder();
        try(FileReader reader = new FileReader(configurationFile)) {
            BufferedReader configReader = new BufferedReader(reader);
            String line;
            while ((line = configReader.readLine()) != null) {
                configString.append(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Failed to find config file", e);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read config file", e);
        }
        return (ConfigurationBuilder) (new Gson())
                .fromJson(configString.toString(), ConfigurationBuilder.class);
    }
}
