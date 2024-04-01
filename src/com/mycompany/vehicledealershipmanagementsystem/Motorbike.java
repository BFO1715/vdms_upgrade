package com.mycompany.vehicledealershipmanagementsystem;

import java.io.Serializable;
/**
 *
 * @author bethan
 */
// Motorbike class
public class Motorbike extends Vehicle implements Serializable {
    private boolean hasLuggageBox;

    public Motorbike(String make, String model, int year, String gearboxType, String color, int mileage, String vin) {
        super(make, model, year, gearboxType, color, mileage, vin);
    }

    public boolean hasLuggageBox() { return hasLuggageBox; }

    public void addLuggageBox() { this.hasLuggageBox = true; }
    public void removeLuggageBox() { this.hasLuggageBox = false; }

    @Override
    public String toString() {
        return "Motorbike{" +
                "hasLuggageBox=" + hasLuggageBox +
                ", " + super.toString() +
                '}';
    }
}
