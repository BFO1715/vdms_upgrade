package com.mycompany.vehicledealershipmanagementsystem;

import java.io.Serializable;
/**
 *
 * @author bethan
 */
// Base Vehicle class
public abstract class Vehicle implements Serializable {
    private final String make;
    private final String model;
    private final int year;
    private final String gearboxType;
    private String color;
    private int mileage;
    private final String vin;

    public Vehicle(String make, String model, int year, String gearboxType, String color, int mileage, String vin) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.gearboxType = gearboxType;
        this.color = color;
        this.mileage = mileage;
        this.vin = vin;
    }

    // Getters
    public String getMake() { return make; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public String getGearboxType() { return gearboxType; }
    public String getColor() { return color; }
    public int getMileage() { return mileage; }
    public String getVin() { return vin; }

    // Setters
    public void setColor(String color) { this.color = color; }
    public void setMileage(int mileage) { this.mileage = mileage; }

    @Override
    public String toString() {
        return "Vehicle{" +
                "make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", gearboxType='" + gearboxType + '\'' +
                ", color='" + color + '\'' +
                ", mileage=" + mileage +
                ", vin='" + vin + '\'' +
                '}';
    }
}
