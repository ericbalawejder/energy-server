package com.ericbalawejder.resources;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.validator.constraints.NotEmpty;

import com.ericbalawejder.jackson.model.SensorReading;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class EnergyResource {
    public class CalculationResult {
        @NotEmpty
        private Double energy;

        @NotEmpty
        private String units;

        public CalculationResult(Double energy, String units) {
            this.energy = Math.round(energy * 1000) / 1000.0d;
            this.units = units;
        }

        @JsonProperty
        public Double getEnergy() {
            // round?
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

    public class ApiResponse {
        @NotEmpty
        private CalculationResult results;

        public ApiResponse(CalculationResult calc) {
            this.results = calc;
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

    @POST
    public ApiResponse getEnergyUsage(@FormParam("starttime") Optional<Integer> startTime,
            @FormParam("endtime") Optional<Integer> endTime) {
        try {
            if (!startTime.isPresent()) {
                throw new RuntimeException("starttime parameter is required");
            }
            if (!endTime.isPresent()) {
                throw new RuntimeException("endtime parameter is required");
            }
            Integer start = startTime.get();
            Integer end = endTime.get();

            // Create JsonParser object.
            JsonParser jsonParser = new JsonFactory().createParser(new File("sensors.json"));

            // Loop through the tokens.
            SensorReading sensorReading = new SensorReading();
            double energy = energyMetered(jsonParser, sensorReading, start, end);
            // System.out.println(sensorReading);
            CalculationResult calculated = kilowattHour(energy);
            jsonParser.close();
            return new ApiResponse(calculated);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private double energyMetered(JsonParser jsonParser, SensorReading sensor, int start, int end)
            throws JsonParseException, IOException {

        double sum = 0;
        int volts = 0;
        int amps = 0;

        // Loop through the JsonTokens
        while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
            String tokenName = jsonParser.getCurrentName();
            if ("current".equals(tokenName)) {
                jsonParser.nextToken();
                sensor.setCurrent(jsonParser.getIntValue());
            } else if ("voltage".equals(tokenName)) {
                jsonParser.nextToken();
                sensor.setVoltage(jsonParser.getIntValue());
            } else if ("time".equals(tokenName)) {
                jsonParser.nextToken();
                sensor.setTime(jsonParser.getIntValue());
                if (sensor.getTime() >= start && sensor.getTime() <= end) {
                    // Start collecting data.
                    volts = sensor.getVoltage();
                    amps = sensor.getCurrent();
                    sum += (volts * amps) / 1000.0;
                }
            }
        }
        return sum;
    }

    // Make parameter for units in energyMetered.
    private CalculationResult kilowattHour(double value) {
        double kWh = (1.0 / 3600) * value;
        // {"results":{"energy":307.668,"units":"kWh"}}
        // String stringValue = "{\"results\":{\"energy\":" + String.format("%.3f", kWh)
        // + ",\"units\":\"kWh\"}}\n";
        // return stringValue;
        return new CalculationResult(kWh, "kWh");
    }

    /*
     * private CalculationResult joules(double value) { // Place conversion here
     * return }
     * 
     * private CalculationResult wattHour(double value) { // Place conversion here
     * // (5.0 / 18) * value return }
     * 
     * private CalculationResult electronVolt(double value) { // Place conversion
     * here return }
     */
}
