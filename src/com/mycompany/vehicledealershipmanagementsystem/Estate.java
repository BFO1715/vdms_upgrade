package com.mycompany.vehicledealershipmanagementsystem;

import java.io.Serializable;
/**
 *
 * @author bethan
 */
// Estate Car class
public class Estate extends Car implements Serializable {
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
