package com.mycompany.vehicledealershipmanagementsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import devtools.io.Data;
import devtools.io.DataManager;
import java.io.Serializable;
import java.time.Year;
/**
 *
 * @author bethan
 */
// Main class to interact with system operator
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
                    "3. Search vehicles \n" +
                    "4. Print all vehicles \n" +
                    "5. Delete a vehicle \n" +
                    "6. Exit") ;
            input = scanner.nextLine();

            switch (input) {
                case "1":
                    addVehicle(scanner);
                    break;
                case "2":
                    updateVehicle(scanner);
                    break;
                case "3":
                    searchVehicles(scanner);
                    break;
                case "4":
                    printVehicles();
                    break;
                case "5":
                    deleteVehicle(scanner);
                    break;
                case "6":
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
            // Convert valid options to lowercase
            String bodyType = getValidInput(scanner, new String[]{"saloon", "estate", "hatchback", "suv"}, "Enter body type (saloon/estate/hatchback/SUV):", "Invalid option, please select from options provided.");

            switch (bodyType) {
                case "estate":
                    vehicle = new Estate(make, model, year, gearboxType, color, mileage, vin, bodyType);
                    boolean hasThirdRowSeat = getYesNoInput(scanner, "Does the estate have a third row seat? (yes/no):");
                    if (hasThirdRowSeat) {
                        ((Estate) vehicle).addThirdRowSeat();
                    }
                    break;
                case "suv":
                    vehicle = new SUV(make, model, year, gearboxType, color, mileage, vin, bodyType);
                    boolean hasAllWheelDrive = getYesNoInput(scanner, "Does the SUV have all-wheel drive? (yes/no):");
                    if (hasAllWheelDrive) {
                        ((SUV) vehicle).addAllWheelDrive();
                    }
                    break;
                default:
                    vehicle = new Car(make, model, year, gearboxType, color, mileage, vin, bodyType);
                    break;
            }
            addCarOptions(scanner, (Car)vehicle);
        } else if ("motorbike".equals(type)) {
            vehicle = new Motorbike(make, model, year, gearboxType, color, mileage, vin);
            // Add the step to ask for the luggage box option
            boolean hasLuggageBox = getYesNoInput(scanner, "Does the motorbike have a luggage box? (yes/no):");
            if (hasLuggageBox) {
                ((Motorbike) vehicle).addLuggageBox();
            }
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
            return;
        }

        String header = String.format("%-10s | %-15s | %-10s | %-5s | %-10s | %-8s | %-7s | %-25s", 
                                      "Type", "Make", "Model", "Year", "Gearbox", "Color", "Mileage", "Extras");
        System.out.println(header);
        System.out.println(new String(new char[header.length()]).replace("\0", "-")); // Dynamic line separator

        for (Vehicle vehicle : vehicles) {
            String type = vehicle instanceof Car ? "Car" : "Motorbike";
            List<String> extraFeatures = new ArrayList<>();

            if (vehicle instanceof Car) {
                Car car = (Car) vehicle;
                if (car.hasSatNav()) extraFeatures.add("SatNav");
                if (car.hasParkingSensors()) extraFeatures.add("Sensors");
                if (car.hasTowBar()) extraFeatures.add("TowBar");
                if (car.hasRoofRack()) extraFeatures.add("RoofRack");
            }

            // Check for SUV features
            if (vehicle instanceof SUV) {
                SUV suv = (SUV) vehicle;
                if (suv.hasAllWheelDrive()) extraFeatures.add("All-Wheel Drive");
            }

            // Check for Estate features
            if (vehicle instanceof Estate) {
                Estate estate = (Estate) vehicle;
                if (estate.hasThirdRowSeat()) extraFeatures.add("Third Row Seat");
            }

            if (vehicle instanceof Motorbike) {
                Motorbike bike = (Motorbike) vehicle;
                if (bike.hasLuggageBox()) extraFeatures.add("Luggage Box");
            }

            String extras = String.join(", ", extraFeatures);

            System.out.printf("%-10s | %-15s | %-10s | %-5d | %-10s | %-8s | %-7d | %-25s%n", 
                              type, 
                              vehicle.getMake(), 
                              vehicle.getModel(), 
                              vehicle.getYear(), 
                              vehicle.getGearboxType(), 
                              vehicle.getColor(), 
                              vehicle.getMileage(), 
                              extras);
        }
    }

    private void searchVehicles(Scanner scanner) {
        System.out.println("Search by: \n1. VIN\n2. Make\n3. Model");
        String choice = scanner.nextLine().trim();
        List<Vehicle> foundVehicles = new ArrayList<>();

        switch (choice) {
            case "1":
                System.out.println("Enter VIN:");
                String vin = scanner.nextLine().trim();
                foundVehicles = vehicles.stream()
                                        .filter(v -> v.getVin().equals(vin))
                                        .collect(Collectors.toList());
                break;
            case "2":
                System.out.println("Enter Make:");
                String make = scanner.nextLine().trim();
                foundVehicles = vehicles.stream()
                                        .filter(v -> v.getMake().equalsIgnoreCase(make))
                                        .collect(Collectors.toList());
                break;
            case "3":
                System.out.println("Enter Model:");
                String model = scanner.nextLine().trim();
                foundVehicles = vehicles.stream()
                                        .filter(v -> v.getModel().equalsIgnoreCase(model))
                                        .collect(Collectors.toList());
                break;
            default:
                System.out.println("Invalid option, please try again.");
                return;
        }

        if (foundVehicles.isEmpty()) {
            System.out.println("No vehicles found.");
        } else {
            System.out.println("Found Vehicles:");
            foundVehicles.forEach(System.out::println);
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

    // Input validation methods
    private String getValidInput(Scanner scanner, String[] validOptions, String prompt, String errorMessage) {
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

    private boolean getYesNoInput(Scanner scanner, String prompt) {
        String input;
        do {
            System.out.println(prompt);
            input = scanner.nextLine().trim().toLowerCase();
        } while (!input.equals("yes") && !input.equals("no"));
        return input.equals("yes");
    }

    private int getInputYear(Scanner scanner, int min, int max) {
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

    private int getInputPositiveInteger(Scanner scanner, String prompt, int min, int max) {
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
            final String finalVin = vin;
            if (vin.length() != 7 || vehicles.stream().anyMatch(v -> v.getVin().equals(finalVin))) {
                System.out.println("VIN must be 7 digits long and unique. Try again.");
                vin = ""; // Reset vin to trigger the loop again if validation fails
            }
        } while (vin.isEmpty());
        return vin;
    }
}