package com.server.energy.resources;

public class ApiResponse {

    private final CalculationResult results;

    public ApiResponse(CalculationResult calculation) {
        this.results = calculation;
    }

    public CalculationResult getResults() {
        return new CalculationResult(results);
    }

}
