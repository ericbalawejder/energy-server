package com.ericbalawejder.energy_server;

import com.ericbalawejder.dropwizard.config.EnergyServerConfig;
import com.ericbalawejder.resources.EnergyResource;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

// Stream JSON file to handle huge input or slow connection. In this case the JSON needs to be
// read as it comes from input part by part. The side effect is that you are able to read 
// corrupted JSON arrays in some kind of comfortable way.
public class StreamReadSensorData extends Application<EnergyServerConfig> {
    public static void main(String[] args) throws Exception {
        new StreamReadSensorData().run(args);
    }

    @Override
    public String getName() {
        return "energy-server";
    }

    @Override
    public void initialize(Bootstrap<EnergyServerConfig> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(EnergyServerConfig configuration, Environment environment) {
        final EnergyResource resource = new EnergyResource();
        environment.jersey().register(resource);
    }
}
