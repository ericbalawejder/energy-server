package com.ericbalawejder.energy_server;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.ericbalawejder.jackson.model.SensorReading;

// Stream JSON file to handle huge input or slow connection. In this case the JSON needs to be
// read as it comes from input part by part. The side effect is that you are able to read 
// corrupted JSON arrays in some kind of comfortable way.
public class StreamReadSensorData {

    public static void main(String[] args) throws JsonParseException, IOException {

        // Create JsonParser object.
        JsonParser jsonParser = new JsonFactory().createParser(new File("sensors.json"));

        // Loop through the tokens.
        SensorReading sensorReading = new SensorReading();
        double energy = energyMetered(jsonParser, sensorReading, 1234, 5678);
        // System.out.println(sensorReading);
        kilowattHour(energy);
        jsonParser.close();
    }

    private static double energyMetered(JsonParser jsonParser, SensorReading sensor, int start, int end)
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

    private static void kilowattHour(double value) {
        double kWh = (1.0 / 3600) * value;
        System.out.println("energy: " + String.format("%.3f", kWh) + ", units: kWh");
    }

    private static void joules(double value) {
        // Place conversion here
    }

    private static void wattHour(double value) {
        // Place conversion here
        // (5.0 / 18) * value
    }

    private static void electronVolt(double value) {
        // Place conversion here
    }
}
