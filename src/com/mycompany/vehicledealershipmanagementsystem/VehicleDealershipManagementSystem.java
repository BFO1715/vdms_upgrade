/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.vehicledealershipmanagementsystem;

import java.util.Scanner;
import devtools.io.Data;
import devtools.io.DataManager;
import java.io.Serializable;

/**
 *
 * @author bethan
 */
// Main class to interact with system operator
public class VehicleDealershipManagementSystem implements Serializable {
    @Data
    public static Vehicle[] vehicles = new Vehicle[10];
    public static int vehicleCount = 0;

    public static void main(String[] args) {
        
        DataManager.start(VehicleDealershipManagementSystem.class);
        
        Scanner scanner = new Scanner(System.in);
        String input;

        do {
            System.out.println("Select action: \n1. Add a new vehicle \n2. Update vehicle \n3. Exit");
            input = scanner.nextLine();

            switch (input) {
                case "1":
                    addVehicle(scanner);
                    break;
                case "2":
                    updateVehicle(scanner);
                    break;
                case "3":
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        } while (!"3".equals(input));

        printVehicles();
        scanner.close();
    }

    public static void addVehicle(Scanner scanner) {
        String type = getValidInput(scanner, new String[]{"car", "motorbike"}, "Enter type of vehicle (car/motorbike):", "Invalid option, please select from options provided.");

        System.out.println("Enter make:");
        String make = scanner.nextLine();
        System.out.println("Enter model:");
        String model = scanner.nextLine();
        System.out.println("Enter year:");
        int year = Integer.parseInt(scanner.nextLine());
        String gearboxType = getValidInput(scanner, new String[]{"manual", "auto"}, "Enter gearbox type (manual/auto):", "Invalid option, please select from options provided.");
        System.out.println("Enter color:");
        String color = scanner.nextLine();
        System.out.println("Enter mileage:");
        int mileage = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter VIN:");
        String vin = scanner.nextLine();

        if ("car".equalsIgnoreCase(type)) {
            String bodyType = getValidInput(scanner, new String[]{"saloon", "estate", "hatchback", "SUV"}, "Enter body type (saloon/estate/hatchback/SUV):", "Invalid option, please select from options provided.");

            Car car = null;
            if ("SUV".equalsIgnoreCase(bodyType)) {
                car = new SUV(make, model, year, gearboxType, color, mileage, vin, bodyType);
                if (getYesNoInput(scanner, "Add all-wheel drive? (yes/no)")) {
                    ((SUV) car).addAllWheelDrive();
                }
            } else if ("estate".equalsIgnoreCase(bodyType)) {
                car = new Estate(make, model, year, gearboxType, color, mileage, vin, bodyType);
                if (getYesNoInput(scanner, "Add third-row seat? (yes/no)")) {
                    ((Estate) car).addThirdRowSeat();
                }
            } else {
                car = new Car(make, model, year, gearboxType, color, mileage, vin, bodyType);
            }

            addCarOptions(scanner, car);
            vehicles[vehicleCount++] = car;
        } else if ("motorbike".equalsIgnoreCase(type)) {
            Motorbike motorbike = new Motorbike(make, model, year, gearboxType, color, mileage, vin);
            if (getYesNoInput(scanner, "Add luggage box? (yes/no)")) {
                motorbike.addLuggageBox();
            }
            vehicles[vehicleCount++] = motorbike;
        }

        if (vehicleCount >= vehicles.length) {
            resizeArray();
        }
    }

    public static void resizeArray() {
        Vehicle[] newVehicles = new Vehicle[vehicles.length * 2];
        System.arraycopy(vehicles, 0, newVehicles, 0, vehicles.length);
        vehicles = newVehicles;
        System.out.println("Array resized: New capacity is " + vehicles.length);
    }

    public static void printVehicles() {
        System.out.println("Vehicles in the system:");
        for (int i = 0; i < vehicleCount; i++) {
            System.out.println(vehicles[i]);
        }
    }

    public static void updateVehicle(Scanner scanner) {
        System.out.println("Enter the VIN of the vehicle to update:");
        String vin = scanner.nextLine();

        for (int i = 0; i < vehicleCount; i++) {
            if (vehicles[i].getVin().equals(vin)) {
                if (vehicles[i] instanceof Motorbike) {
                    String choice = getValidInput(scanner, new String[]{"add", "remove", "no"}, "Do you want to add/remove luggage box? (add/remove/no)", "Invalid option, please select from options provided.");
                    if ("add".equalsIgnoreCase(choice)) {
                        ((Motorbike) vehicles[i]).addLuggageBox();
                    } else if ("remove".equalsIgnoreCase(choice)) {
                        ((Motorbike) vehicles[i]).removeLuggageBox();
                    }
                } else if (vehicles[i] instanceof Car) {
                    System.out.println("Enter new color (or 'no' to skip):");
                    String color = scanner.nextLine();
                    if (!"no".equalsIgnoreCase(color)) {
                        vehicles[i].setColor(color);
                    }

                    System.out.println("Enter new mileage (or -1 to skip):");
                    int mileage = scanner.nextInt();
                    if (mileage != -1) {
                        vehicles[i].setMileage(mileage);
                    }
                    scanner.nextLine(); // Clear the buffer
                }
                System.out.println("Vehicle updated: " + vehicles[i]);
                return;
            }
        }
        System.out.println("Vehicle with VIN " + vin + " not found.");
    }

    public static void addCarOptions(Scanner scanner, Car car) {
        if (getYesNoInput(scanner, "Add sat nav? (yes/no)")) {
            car.addSatNav();
        }
        if (getYesNoInput(scanner, "Add parking sensors? (yes/no)")) {
            car.addParkingSensors();
        }
        if (getYesNoInput(scanner, "Add a tow bar? (yes/no)")) {
            car.addTowBar();
        }
        if (getYesNoInput(scanner, "Add a roof rack? (yes/no)")) {
            car.addRoofRack();
        }
    }

    public static boolean getYesNoInput(Scanner scanner, String prompt) {
        String input;
        do {
            System.out.println(prompt);
            input = scanner.nextLine();
        } while (!input.equalsIgnoreCase("yes") && !input.equalsIgnoreCase("no"));
        return "yes".equalsIgnoreCase(input);
    }

    public static String getValidInput(Scanner scanner, String[] validOptions, String prompt, String errorMessage) {
        while (true) {
            System.out.println(prompt);
            String input = scanner.nextLine();
            for (String validOption : validOptions) {
                if (validOption.equalsIgnoreCase(input)) {
                    return input;
                }
            }
            System.out.println(errorMessage);
        }
    }
}



