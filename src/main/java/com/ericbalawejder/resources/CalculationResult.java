package com.ericbalawejder.resources;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CalculationResult {
    @NotEmpty
    private Double energy;

    @NotEmpty
    private String units;

    // TODO Alternative for Math.round()
    public CalculationResult(Double energy, String units) {
        this.energy = Math.round(energy * 1000) / 1000.0d;
        this.units = units;
    }

    @JsonProperty
    public Double getEnergy() {
        return energy;
    }

    @JsonProperty
    public void setEnergy(Double energy) {
        this.energy = energy;
    }

    @JsonProperty
    public String getUnits() {
        return units;
    }

    @JsonProperty
    public void setUnits(String units) {
        this.units = units;
    }

}
