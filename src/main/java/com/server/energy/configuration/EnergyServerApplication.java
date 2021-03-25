package com.server.energy.configuration;

import com.server.energy.resources.EnergyResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class EnergyServerApplication extends Application<EnergyServerConfiguration> {

    public static void main(String[] args) throws Exception {
        new EnergyServerApplication().run(args);
    }

    @Override
    public String getName() {
        return "energy-server";
    }

    @Override
    public void initialize(Bootstrap<EnergyServerConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(EnergyServerConfiguration configuration, Environment environment) {
        final EnergyResource resource = new EnergyResource();
        environment.jersey().register(resource);
    }

}
