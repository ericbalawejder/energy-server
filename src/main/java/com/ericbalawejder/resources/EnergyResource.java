package com.ericbalawejder.resources;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ericbalawejder.jackson.model.SensorReading;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class EnergyResource {

    @POST
    public String getEnergyUsage(@FormParam("starttime") Optional<Integer> startTime,
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
            String calculated = kilowattHour(energy);
            jsonParser.close();
            return calculated;
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

    private String kilowattHour(double value) {
        double kWh = (1.0 / 3600) * value;
        String stringValue = "{\"results\":{\"energy\":" + String.format("%.3f", kWh) + ",\"units\":\"kWh\"}}\n";
        return stringValue;
    }

    /*
     * private CalculationResult joules(double value) {
     * // Place conversion here
     * return
     * }
     * 
     * private CalculationResult wattHour(double value) {
     * // Place conversion here
     * // (5.0 / 18) * value 
     * return
     * }
     * 
     * private CalculationResult electronVolt(double value) {
     * // Place conversion here 
     * return
     * }
     */
}
