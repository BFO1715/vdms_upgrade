/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.vehicledealershipmanagementsystem;

/**
 *
 * @author bethan
 */
// Estate Car class
public class Estate extends Car {
    private boolean hasThirdRowSeat;

    public Estate(String make, String model, int year, String gearboxType, String color, int mileage, String vin, String bodyType) {
        super(make, model, year, gearboxType, color, mileage, vin, bodyType);
    }

    public boolean hasThirdRowSeat() { return hasThirdRowSeat; }

    public void addThirdRowSeat() { this.hasThirdRowSeat = true; }

    @Override
    public String toString() {
        return "Estate{" +
                "hasThirdRowSeat=" + hasThirdRowSeat +
                ", " + super.toString() +
                '}';
    }
}
