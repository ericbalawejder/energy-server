package com.server.energy.resources;

import com.fasterxml.jackson.core.JsonFactory;
import com.server.energy.entity.SensorReading;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EnergyResourceTest {

    private EnergyResource energyResource;

    @BeforeEach
    void setUp() {
        energyResource = new EnergyResource();
    }

    @Test
    void getEnergyUsageTest() {
        // sensors.json is hardcoded into this method.
        // TODO parameterize the file.
    }

    @Test
    void getEnergyUsageStartTimeNotPresent() {
        RuntimeException expected =
                assertThrows(
                        RuntimeException.class,
                        () -> energyResource.getEnergyUsage(Optional.empty(), Optional.of(4567)));

        assertThat(expected)
                .hasMessage("starttime parameter is required");
    }

    @Test
    void getEnergyUsageEndTimeNotPresent() {
        RuntimeException expected =
                assertThrows(
                        RuntimeException.class,
                        () -> energyResource.getEnergyUsage(Optional.of(1234), Optional.empty()));

        assertThat(expected)
                .hasMessage("endtime parameter is required");
    }

    @Test
    void energyMeteredTest() throws IOException {
        final double expected = 0.599 + 1.178 + 1.77;

        final double actual = energyResource.energyMetered(
                        new JsonFactory().createParser(new File("sensor-data-test.json")),
                        new SensorReading(),
                        478,
                        480
        );

        assertEquals(expected, actual);
    }

    @Test
    void energyMeteredTestIsZero() throws IOException {
        final double expected = 0;

        final double actual = energyResource.energyMetered(
                new JsonFactory().createParser(new File("sensor-data-test.json")),
                new SensorReading(),
                300,
                400
        );

        assertEquals(expected, actual);
    }

    @Test
    void kilowattHourTest() {
        final CalculationResult expected = new CalculationResult(0.003, "kWh");

        final CalculationResult actual = EnergyResource.kilowattHour(10.0);

        assertEquals(expected, actual);
    }

    @Test
    void roundTest() {
        final double expected = 123.46;

        final double actual = EnergyResource.round(123.4567890, 2);

        assertEquals(expected, actual);
    }

    @Test
    void roundTestAllNines() {
        final double expected = 100.00000;

        final double actual = EnergyResource.round(99.9999999999, 5);

        assertEquals(expected, actual);
    }

    @Test
    void testRoundThrowsException() {
        IllegalArgumentException expected =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> EnergyResource.round(12.2, -1));

        assertThat(expected)
                .hasMessage("-1 < 0");
    }

}
