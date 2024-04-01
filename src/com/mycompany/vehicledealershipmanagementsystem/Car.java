package com.mycompany.vehicledealershipmanagementsystem;

import java.io.Serializable;
/**
 *
 * @author bethan
 */
// Car class
public class Car extends Vehicle implements Serializable {
    private String bodyType;
    private boolean hasSatNav;
    private boolean hasParkingSensors;
    private boolean hasTowBar;
    private boolean hasRoofRack;

    public Car(String make, String model, int year, String gearboxType, String color, int mileage, String vin, String bodyType) {
        super(make, model, year, gearboxType, color, mileage, vin);
        this.bodyType = bodyType;
    }

    // Getters and Setters for Car-specific fields
    public String getBodyType() { return bodyType; }
    public boolean hasSatNav() { return hasSatNav; }
    public boolean hasParkingSensors() { return hasParkingSensors; }
    public boolean hasTowBar() { return hasTowBar; }
    public boolean hasRoofRack() { return hasRoofRack; }

    // Car-specific methods
    public void addSatNav() { this.hasSatNav = true; }
    public void addParkingSensors() { this.hasParkingSensors = true; }
    public void addTowBar() { this.hasTowBar = true; }
    public void addRoofRack() { this.hasRoofRack = true; }

    @Override
    public String toString() {
        return "Car{" +
                "bodyType='" + bodyType + '\'' +
                ", hasSatNav=" + hasSatNav +
                ", hasParkingSensors=" + hasParkingSensors +
                ", hasTowBar=" + hasTowBar +
                ", hasRoofRack=" + hasRoofRack +
                ", " + super.toString() +
                '}';
    }
}
