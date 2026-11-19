package parking.app;

import parking.model.Car;
import parking.model.Vehicle;
import parking.service.ParkingService;

import java.util.Arrays;

public class ParkingApp {

    public static void main(String[] args) {
        System.out.println("===============================================================");
        System.out.println("TASK 10: PACKAGES, ACCESS MODIFIERS & ENCAPSULATION");
        System.out.println("Student: Korasika Sudheer | Register Number: 192324313");
        System.out.println("===============================================================\n");

        // 1. Cross-package interaction
        ParkingService service = new ParkingService(new int[]{2, 3});
        Vehicle car1 = new Car("TN01-PKG-1001");
        int[] allocated = service.park(car1);
        System.out.println("Parked " + car1.getRegNo() + " at " + Arrays.toString(allocated));

        // 2. Encapsulation & Defensive validation
        System.out.println("\n--- Defensive Invariant Validation ---");
        try {
            car1.calculateFee(-15);
        } catch (IllegalArgumentException e) {
            System.out.println("Defensive Check Passed: Caught negative duration -> " + e.getMessage());
        }

        try {
            new Car("   ");
        } catch (IllegalArgumentException e) {
            System.out.println("Defensive Check Passed: Caught blank registration -> " + e.getMessage());
        }

        // 3. Wrapper class Integer cache pitfall
        System.out.println("\n--- Wrapper Class Autoboxing & == Pitfall Demonstration ---");
        Integer smallA = 50;
        Integer smallB = 50;
        System.out.println("smallA (50) == smallB (50): " + (smallA == smallB) + " (Cached range [-128, 127])");

        Integer largeA = 200;
        Integer largeB = 200;
        System.out.println("largeA (200) == largeB (200): " + (largeA == largeB) + " (Different heap references!)");
        System.out.println("largeA.equals(largeB): " + largeA.equals(largeB) + " (Correct value comparison via equals)");
    }
}
