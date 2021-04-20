package com.server.energy.resources;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CalculationResult result = (CalculationResult) o;
        return Objects.equals(energy, result.energy) && Objects.equals(units, result.units);
    }

    @Override
    public int hashCode() {
        return Objects.hash(energy, units);
    }

    @Override
    public String toString() {
        return "CalculationResult{" +
                "energy=" + energy +
                ", units='" + units + '\'' +
                '}';
    }

}
