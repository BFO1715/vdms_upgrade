/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.vehicledealershipmanagementsystem;

/**
 *
 * @author bethan
 */
// Motorbike class
public class Motorbike extends Vehicle {
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
