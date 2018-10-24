package com.ericbalawejder.dropwizard.config;

import org.hibernate.validator.constraints.NotEmpty;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

public class EnergyServerConfig extends Configuration {

    @NotEmpty
    private String template;

    public EnergyServerConfig() {

    }

    public EnergyServerConfig(String template) {
        this.template = template;
    }

    @JsonProperty
    public void setTemplate(String template) {
        this.template = template;
    }

    @JsonProperty
    public String getTemplate() {
        return this.template;
    }
}
