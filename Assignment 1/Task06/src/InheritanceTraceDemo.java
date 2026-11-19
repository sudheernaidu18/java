/**
 * Task 06: Design the Inheritance Hierarchy
 * Demonstrates constructor execution tracing, class hierarchy, and composition alternative.
 *
 * Student Name: Korasika Sudheer
 * Register Number: 192324313
 */
public class InheritanceTraceDemo {

    // 1. Inheritance Hierarchy
    static abstract class Vehicle {
        private final String regNo;

        protected Vehicle(String regNo) {
            System.out.println("  [Step 1] Vehicle constructor executed for: " + regNo);
            this.regNo = regNo;
        }

        public String getRegNo() { return regNo; }
        protected abstract double ratePerHour();

        public double calculateFee(int minutes) {
            int hours = (minutes + 59) / 60;
            return hours * ratePerHour();
        }
    }

    static class Car extends Vehicle {
        public Car(String regNo) {
            super(regNo);
            System.out.println("  [Step 2] Car constructor executed.");
        }

        @Override
        protected double ratePerHour() { return 30.0; }
    }

    interface Chargeable {
        double chargingFee(int kWh);
    }

    static class ElectricCar extends Car implements Chargeable {
        public ElectricCar(String regNo) {
            super(regNo);
            System.out.println("  [Step 3] ElectricCar constructor executed.");
        }

        @Override
        public double chargingFee(int kWh) { return kWh * 12.0; }
    }

    // 2. Composition Alternative
    static class VehicleType {
        private final String name;
        private final double ratePerHour;
        public VehicleType(String name, double rate) { this.name = name; this.ratePerHour = rate; }
        public double getRatePerHour() { return ratePerHour; }
        public String getName() { return name; }
    }

    static class CompositeVehicle {
        private final String regNo;
        private final VehicleType type; // has-a VehicleType
        public CompositeVehicle(String regNo, VehicleType type) { this.regNo = regNo; this.type = type; }
        public double calculateFee(int minutes) {
            return ((minutes + 59) / 60) * type.getRatePerHour();
        }
    }

    public static void main(String[] args) {
        System.out.println("===============================================================");
        System.out.println("TASK 06: INHERITANCE HIERARCHY & CONSTRUCTOR TRACE VALIDATION");
        System.out.println("Student: Korasika Sudheer | Register Number: 192324313");
        System.out.println("===============================================================\n");

        System.out.println("--- Tracing ElectricCar Constructor Execution Chain ---");
        ElectricCar ec = new ElectricCar("TN03C3");
        System.out.println("Successfully created: " + ec.getClass().getSimpleName() + " with plate " + ec.getRegNo());

        System.out.println("\n--- Testing Composition Alternative ---");
        VehicleType heavyTruck = new VehicleType("HeavyTruck", 55.0);
        CompositeVehicle truck = new CompositeVehicle("TN09TR7777", heavyTruck);
        System.out.println("Composite vehicle type: " + heavyTruck.getName() + ", 120 mins fee: Rs. " + truck.calculateFee(120));
    }
}
