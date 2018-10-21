package com.ericbalawejder.jackson.model;

// SensorReading object represents an individual JSON object reading.
public class SensorReading {

    // Seconds
    private int time;
    // Amps
    private int current;
    // Volts
    private int voltage;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getVoltage() {
        return voltage;
    }

    public void setVoltage(int voltage) {
        this.voltage = voltage;
    }

    @Override
    public String toString() {
        StringBuilder reading = new StringBuilder();
        reading.append("**** Sensor Reading Details ****\n");
        reading.append("time(s)        = " + getTime() + "\n");
        reading.append("current(amps)  = " + getCurrent() + "\n");
        reading.append("voltage(volts) = " + getVoltage() + "\n");
        reading.append("********************************");
        return reading.toString();
    }
}
