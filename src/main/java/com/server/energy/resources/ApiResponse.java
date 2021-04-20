package com.server.energy.resources;

import java.util.Objects;

public class ApiResponse {

    private final CalculationResult results;

    public ApiResponse(CalculationResult calculation) {
        this.results = calculation;
    }

    public CalculationResult getResults() {
        return new CalculationResult(results);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiResponse that = (ApiResponse) o;
        return Objects.equals(results, that.results);
    }

    @Override
    public int hashCode() {
        return Objects.hash(results);
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "results=" + results +
                '}';
    }

}
