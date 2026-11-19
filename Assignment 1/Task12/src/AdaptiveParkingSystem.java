/**
 * Task 12: Day-4 Design Challenge - Re-evaluate and Adapt the Design
 * Implements E-Bikes (flat fee), Delivery Vans (double car rate 9 AM - 5 PM),
 * Visitor One-Day Pass, and 5-Floor Non-Uniform Jagged Facility.
 *
 * Student Name: Korasika Sudheer
 * Register Number: 192324313
 */
import java.time.LocalTime;
import java.util.*;

public class AdaptiveParkingSystem {

    // ==========================================
    // 1. VEHICLE HIERARCHY
    // ==========================================
    public static abstract class Vehicle {
        private final String regNo;

        protected Vehicle(String regNo) {
            this.regNo = regNo.trim().toUpperCase();
        }

        public final String getRegNo() { return regNo; }

        protected abstract double baseRatePerHour();

        // Standard duration-based overload
        public double calculateFee(int minutes) {
            int hours = (minutes + 59) / 60;
            return hours * baseRatePerHour();
        }

        // Temporal overload for time-sensitive pricing
        public double calculateFee(int minutes, LocalTime entryTime) {
            return calculateFee(minutes); // Default delegates to standard fee
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + " [" + regNo + "]";
        }
    }

    public static class TwoWheeler extends Vehicle {
        public TwoWheeler(String regNo) { super(regNo); }
        @Override protected double baseRatePerHour() { return 10.0; }
    }

    public static class Car extends Vehicle {
        public Car(String regNo) { super(regNo); }
        @Override protected double baseRatePerHour() { return 30.0; }
    }

    // NEW CATEGORY 1: E-Bike (Charged a flat fee)
    public static class EBike extends Vehicle {
        private static final double FLAT_FEE = 20.0;

        public EBike(String regNo) { super(regNo); }
        @Override protected double baseRatePerHour() { return 0.0; }

        @Override
        public double calculateFee(int minutes) {
            return FLAT_FEE; // Flat fee regardless of duration
        }

        @Override
        public double calculateFee(int minutes, LocalTime entryTime) {
            return FLAT_FEE;
        }
    }

    // NEW CATEGORY 2: Delivery Van (Double car rate during 9 AM - 5 PM)
    public static class DeliveryVan extends Vehicle {
        private static final LocalTime PEAK_START = LocalTime.of(9, 0);
        private static final LocalTime PEAK_END = LocalTime.of(17, 0);

        public DeliveryVan(String regNo) { super(regNo); }
        @Override protected double baseRatePerHour() { return 30.0; }

        @Override
        public double calculateFee(int minutes, LocalTime entryTime) {
            int hours = (minutes + 59) / 60;
            boolean isPeak = !entryTime.isBefore(PEAK_START) && !entryTime.isAfter(PEAK_END);
            double rate = isPeak ? (baseRatePerHour() * 2.0) : baseRatePerHour();
            return hours * rate;
        }
    }

    // ==========================================
    // 2. PASS ABSTRACTION
    // ==========================================
    public interface Pass {
        double applyDiscount(double calculatedFee);
        String getPassType();
    }

    public static class MonthlyPass implements Pass {
        @Override
        public double applyDiscount(double calculatedFee) { return 0.0; } // 100% discount
        @Override
        public String getPassType() { return "MonthlyPass (Free)"; }
    }

    // NEW PRIVILEGE: Visitor One-Day Pass (Flat Rs. 150/day)
    public static class VisitorDayPass implements Pass {
        private static final double DAY_PASS_PRICE = 150.0;
        @Override
        public double applyDiscount(double calculatedFee) {
            return Math.min(calculatedFee, DAY_PASS_PRICE);
        }
        @Override
        public String getPassType() { return "Visitor One-Day Pass (Capped at Rs. 150)"; }
    }

    // ==========================================
    // 3. 5-FLOOR EXTENDED JAGGED FACILITY
    // ==========================================
    public static class ExtendedFacility {
        private final Vehicle[][] floors;

        public ExtendedFacility(int[] slotsPerFloor) {
            floors = new Vehicle[slotsPerFloor.length][];
            for (int f = 0; f < slotsPerFloor.length; f++) {
                floors[f] = new Vehicle[slotsPerFloor[f]];
            }
        }

        public int[] park(Vehicle v) {
            for (int f = 0; f < floors.length; f++) {
                for (int s = 0; s < floors[f].length; s++) {
                    if (floors[f][s] == null) {
                        floors[f][s] = v;
                        return new int[]{f, s};
                    }
                }
            }
            return null; // Full
        }

        public int getTotalCapacity() {
            int total = 0;
            for (Vehicle[] floor : floors) total += floor.length;
            return total;
        }

        public int getFloorCapacity(int floor) {
            return floors[floor].length;
        }
    }
}
