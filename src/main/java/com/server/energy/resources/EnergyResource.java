package com.server.energy.resources;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.server.energy.entity.SensorReading;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class EnergyResource {

    @POST
    public ApiResponse getEnergyUsage(@FormParam("starttime") Optional<Integer> startTime,
                                      @FormParam("endtime") Optional<Integer> endTime) {
        try {
            if (startTime.isEmpty()) {
                throw new RuntimeException("starttime parameter is required");
            }
            if (endTime.isEmpty()) {
                throw new RuntimeException("endtime parameter is required");
            }

            final JsonParser jsonParser = new JsonFactory()
                    .createParser(new File("sensors.json"));

            // Loop through the tokens.
            final SensorReading sensorReading = new SensorReading();
            final double energy =
                    energyMetered(jsonParser, sensorReading, startTime.get(), endTime.get());
            jsonParser.close();
            final CalculationResult calculated = kilowattHour(energy);
            return new ApiResponse(calculated);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Stream JSON file to handle huge input or slow connection. In this case the JSON needs
    // to be read as it comes from input part by part. The side effect is that you are able
    // to read corrupted JSON arrays in some kind of comfortable way.
    private double energyMetered(JsonParser jsonParser, SensorReading sensor, int start, int end)
            throws IOException {

        double sum = 0;

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
                    sum += (sensor.getVoltage() * sensor.getCurrent()) / 1000.0;
                }
            }
        }
        return sum;
    }

    private CalculationResult kilowattHour(double value) {
        final double kWh = (1.0 / 3600) * value;
        return new CalculationResult(round(kWh, 3), "kWh");
    }

    private static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException(places + " < 0");
        }
        final BigDecimal bigdecimal = new BigDecimal(Double.toString(value));
        return bigdecimal.setScale(places, RoundingMode.HALF_UP)
                .doubleValue();
    }

}
