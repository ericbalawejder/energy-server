package com.ericbalawejder.resources;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiResponse {
    @NotEmpty
    private CalculationResult results;

    public ApiResponse(CalculationResult calculation) {
        this.results = calculation;
    }

    @JsonProperty
    public CalculationResult getResults() {
        return results;
    }

    @JsonProperty
    public void setResults(CalculationResult results) {
        this.results = results;
    }

}
