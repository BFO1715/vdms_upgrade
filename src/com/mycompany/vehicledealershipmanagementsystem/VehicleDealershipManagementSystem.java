/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.vehicledealershipmanagementsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import devtools.io.Data;
import devtools.io.DataManager;
import java.io.Serializable;
import java.time.Year;

public class VehicleDealershipManagementSystem implements Serializable {
    @Data
    public List<Vehicle> vehicles = new ArrayList<>();

    public static void main(String[] args) {
        VehicleDealershipManagementSystem system = new VehicleDealershipManagementSystem();
        DataManager.start(system);
        system.run();
    }

    private void run() {
        Scanner scanner = new Scanner(System.in);
        String input;

        do {
            System.out.println("Select action: \n" +
                    "1. Add a new vehicle \n" +
                    "2. Update vehicle \n" +
                    "3. Print all vehicles \n" +
                    "4. Delete a vehicle \n" +
                    "5. Exit");
            input = scanner.nextLine();

            switch (input) {
                case "1":
                    addVehicle(scanner);
                    break;
                case "2":
                    updateVehicle(scanner);
                    break;
                case "3":
                    printVehicles();
                    break;
                case "4":
                    deleteVehicle(scanner);
                    break;
                case "5":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        } while (true);
    }

    private void addVehicle(Scanner scanner) {
        String type = getValidInput(scanner, new String[]{"car", "motorbike"}, "Enter vehicle type (car/motorbike):", "Invalid type, choose car or motorbike.");

        System.out.println("Enter make:");
        String make = scanner.nextLine();
        System.out.println("Enter model:");
        String model = scanner.nextLine();

        int year = getInputYear(scanner, 1900, Year.now().getValue());

        String gearboxType = getValidInput(scanner, new String[]{"manual", "auto"}, "Enter gearbox type (manual/auto):", "Invalid option, please select from options provided.");

        System.out.println("Enter color:");
        String color = scanner.nextLine();

        int mileage = getInputPositiveInteger(scanner, "Enter mileage (0 - 9999999):", 0, 9999999);

        String vin = getUniqueVin(scanner);

        Vehicle vehicle = null;
        if ("car".equals(type)) {
            String bodyType = getValidInput(scanner, new String[]{"saloon", "estate", "hatchback", "SUV"}, "Enter body type (saloon/estate/hatchback/SUV):", "Invalid option, please select from options provided.");
            vehicle = new Car(make, model, year, gearboxType, color, mileage, vin, bodyType);
            addCarOptions(scanner, (Car)vehicle);
        } else if ("motorbike".equals(type)) {
            vehicle = new Motorbike(make, model, year, gearboxType, color, mileage, vin);
        }

        if (vehicle != null) {
            vehicles.add(vehicle);
            System.out.println("Vehicle added successfully.");
        }
    }

    private void updateVehicle(Scanner scanner) {
        System.out.println("Enter the VIN of the vehicle to update:");
        String vin = scanner.nextLine();

        Vehicle vehicle = vehicles.stream().filter(v -> v.getVin().equals(vin)).findFirst().orElse(null);
        if (vehicle != null) {
            System.out.println("Vehicle found: " + vehicle);

            System.out.println("Enter new color (or 'no' to skip):");
            String newColor = scanner.nextLine();
            if (!"no".equalsIgnoreCase(newColor)) {
                vehicle.setColor(newColor);
            }

            int newMileage = getInputPositiveInteger(scanner, "Enter new mileage (or -1 to skip):", -1, 9999999);
            if (newMileage != -1) {
                vehicle.setMileage(newMileage);
            }

            System.out.println("Vehicle updated: " + vehicle);
        } else {
            System.out.println("Vehicle with VIN " + vin + " not found.");
        }
    }

    private void deleteVehicle(Scanner scanner) {
        System.out.println("Enter the VIN of the vehicle to delete:");
        String vin = scanner.nextLine();
        boolean removed = vehicles.removeIf(vehicle -> vehicle.getVin().equals(vin));
        if (removed) {
            System.out.println("Vehicle with VIN " + vin + " deleted.");
        } else {
            System.out.println("Vehicle with VIN " + vin + " not found.");
        }
    }

    private void printVehicles() {
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles currently in the system.");
        } else {
            vehicles.forEach(System.out::println);
        }
    }

    private void addCarOptions(Scanner scanner, Car car) {
        if (getYesNoInput(scanner, "Does the car have sat nav? (yes/no):")) {
            car.addSatNav();
        }
        if (getYesNoInput(scanner, "Does the car have parking sensors? (yes/no):")) {
            car.addParkingSensors();
        }
        if (getYesNoInput(scanner, "Does the car have a tow bar? (yes/no):")) {
            car.addTowBar();
        }
        if (getYesNoInput(scanner, "Does the car have a roof rack? (yes/no):")) {
            car.addRoofRack();
        }
    }

    // Utility methods for input validation
    private static String getValidInput(Scanner scanner, String[] validOptions, String prompt, String errorMessage) {
        String input;
        boolean isValid;
        do {
            System.out.println(prompt);
            input = scanner.nextLine().trim().toLowerCase();
            isValid = java.util.Arrays.stream(validOptions).anyMatch(input::equals);
            if (!isValid) {
                System.out.println(errorMessage);
            }
        } while (!isValid);
        return input;
    }

    private static boolean getYesNoInput(Scanner scanner, String prompt) {
        String input;
        do {
            System.out.println(prompt);
            input = scanner.nextLine().trim().toLowerCase();
        } while (!input.equals("yes") && !input.equals("no"));
        return input.equals("yes");
    }

    private static int getInputYear(Scanner scanner, int min, int max) {
        int year;
        do {
            System.out.println("Enter year (" + min + " - " + max + "):");
            while (!scanner.hasNextInt()) {
                System.out.println("That's not a valid year!");
                scanner.next(); // Important for avoiding infinite loop
            }
            year = scanner.nextInt();
            scanner.nextLine(); // Consume newline
        } while (year < min || year > max);
        return year;
    }

    private static int getInputPositiveInteger(Scanner scanner, String prompt, int min, int max) {
        int number;
        do {
            System.out.println(prompt);
            while (!scanner.hasNextInt()) {
                System.out.println("That's not a number!");
                scanner.next(); // Important for avoiding infinite loop
            }
            number = scanner.nextInt();
            scanner.nextLine(); // Consume newline
        } while (number < min || number > max);
        return number;
    }

    private String getUniqueVin(Scanner scanner) {
    String vin;
    do {
        System.out.println("Enter VIN (7 digits long):");
        vin = scanner.nextLine();
        // Declare a final variable to use within lambda
        final String finalVin = vin;
        if (vin.length() != 7 || vehicles.stream().anyMatch(v -> v.getVin().equals(finalVin))) {
            System.out.println("VIN must be 7 digits long and unique. Try again.");
            vin = ""; // Reset vin to trigger the loop again if validation fails
        }
    } while (vin.isEmpty());
    return vin;
}
}
