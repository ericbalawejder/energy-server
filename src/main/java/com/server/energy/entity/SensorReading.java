package com.server.energy.entity;

public class SensorReading {

    private int time;
    private int current;
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
        return "**** Sensor Reading Details ****\n" +
                "time(s)        = " + getTime() + "\n" +
                "current(amps)  = " + getCurrent() + "\n" +
                "voltage(volts) = " + getVoltage() + "\n" +
                "********************************";
    }

}
