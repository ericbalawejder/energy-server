package com.server.energy.resources;

public class CalculationResult {

    private final Double energy;
    private final String units;

    public CalculationResult(Double energy, String units) {
        this.energy = energy;
        this.units = units;
    }

    public CalculationResult(CalculationResult calculationResult) {
        this.energy = calculationResult.getEnergy();
        this.units = calculationResult.getUnits();
    }

    public Double getEnergy() {
        return energy;
    }

    public String getUnits() {
        return units;
    }

}
