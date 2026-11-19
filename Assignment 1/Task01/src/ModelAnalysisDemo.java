/**
 * Task 01: Understand the Problem and Identify the Objects
 * Demonstrates Object-Oriented Modeling vs Parallel Arrays approach.
 *
 * Student Name: Korasika Sudheer
 * Register Number: 192324313
 */
public class ModelAnalysisDemo {

    // ==========================================
    // DESIGN A: Object-Oriented Vehicle Model
    // ==========================================
    static abstract class Vehicle {
        private final String regNo;
        private final long entryTimeMillis;

        protected Vehicle(String regNo, long entryTimeMillis) {
            this.regNo = regNo;
            this.entryTimeMillis = entryTimeMillis;
        }

        public String getRegNo() { return regNo; }
        public long getEntryTimeMillis() { return entryTimeMillis; }
        public abstract double ratePerHour();

        @Override
        public String toString() {
            return getClass().getSimpleName() + " [regNo=" + regNo + ", rate=" + ratePerHour() + "/hr]";
        }
    }

    static class TwoWheeler extends Vehicle {
        public TwoWheeler(String regNo, long entryTime) { super(regNo, entryTime); }
        @Override public double ratePerHour() { return 10.0; }
    }

    static class Car extends Vehicle {
        public Car(String regNo, long entryTime) { super(regNo, entryTime); }
        @Override public double ratePerHour() { return 30.0; }
    }

    static class ElectricCar extends Car {
        private final double batteryCapacityKWh;

        public ElectricCar(String regNo, long entryTime, double batteryCapacityKWh) {
            super(regNo, entryTime);
            this.batteryCapacityKWh = batteryCapacityKWh;
        }

        public double getBatteryCapacityKWh() { return batteryCapacityKWh; }
        @Override public double ratePerHour() { return 30.0; }

        @Override
        public String toString() {
            return super.toString() + " (Battery: " + batteryCapacityKWh + " kWh)";
        }
    }

    public static void main(String[] args) {
        System.out.println("===============================================================");
        System.out.println("TASK 01: OBJECT-ORIENTED MODELING VS PARALLEL ARRAYS VALIDATION");
        System.out.println("Student: Korasika Sudheer | Register Number: 192324313");
        System.out.println("===============================================================\n");

        // 1. Object-Oriented Polymorphic Array
        System.out.println("--- 1. Object-Oriented Model Instantiation ---");
        Vehicle[] facilitySlots = new Vehicle[3];
        facilitySlots[0] = new TwoWheeler("TN01-AA-1111", System.currentTimeMillis());
        facilitySlots[1] = new Car("TN02-BB-2222", System.currentTimeMillis());
        facilitySlots[2] = new ElectricCar("TN03-CC-3333", System.currentTimeMillis(), 45.0);

        for (int i = 0; i < facilitySlots.length; i++) {
            System.out.println("Slot " + i + " contains: " + facilitySlots[i]);
        }

        // Search by Registration Number in OO Model
        String targetRegNo = "TN02-BB-2222";
        System.out.println("\nSearching for " + targetRegNo + " in OO model...");
        Vehicle foundVehicle = null;
        for (Vehicle v : facilitySlots) {
            if (v != null && v.getRegNo().equals(targetRegNo)) {
                foundVehicle = v;
                break;
            }
        }
        System.out.println("Found vehicle: " + foundVehicle);

        // 2. Parallel Arrays Model Comparison
        System.out.println("\n--- 2. Parallel Arrays Model Representation ---");
        String[] regNos = {"TN01-AA-1111", "TN02-BB-2222", "TN03-CC-3333"};
        String[] types = {"TwoWheeler", "Car", "ElectricCar"};
        double[] rates = {10.0, 30.0, 30.0};
        double[] batteryLevels = {0.0, 0.0, 45.0}; // Required adding a whole new array for EV attribute

        for (int i = 0; i < regNos.length; i++) {
            System.out.println("Index " + i + " -> RegNo: " + regNos[i] + ", Type: " + types[i] +
                    ", Rate: Rs." + rates[i] + ", Battery: " + batteryLevels[i] + " kWh");
        }

        System.out.println("\n--- Evaluation Summary ---");
        System.out.println("OO Model maintains encapsulated state, strong typing, and extensible subclasses.");
        System.out.println("Parallel arrays suffer from index-misalignment risks and rigid signature constraints.");
    }
}
