package com.mycompany.vehicledealershipmanagementsystem;

import java.io.Serializable;
/**
 *
 * @author bethan
 */
// SUV Car class
public class SUV extends Car implements Serializable {
    private boolean hasAllWheelDrive;

    public SUV(String make, String model, int year, String gearboxType, String color, int mileage, String vin, String bodyType) {
        super(make, model, year, gearboxType, color, mileage, vin, bodyType);
    }

    public boolean hasAllWheelDrive() { return hasAllWheelDrive; }

    public void addAllWheelDrive() { this.hasAllWheelDrive = true; }

    @Override
    public String toString() {
        return "SUV{" +
                "hasAllWheelDrive=" + hasAllWheelDrive +
                ", " + super.toString() +
                '}';
    }
}
